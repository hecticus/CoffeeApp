package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class GenericListAdapter extends RecyclerView.Adapter<GenericItemViewHolder> {

    private List<? extends BaseModel> list;
    private BaseListContract.Actions mPresenter;
    private boolean canEdit = false;
    private boolean isDialog = false;

    @DebugLog
    public GenericListAdapter(BaseListContract.Actions mPresenterParam, boolean canEditParam, boolean isDialogParam) {
        list = new ArrayList<>();
        mPresenter = mPresenterParam;
        canEdit = canEditParam;
        isDialog = isDialogParam;
    }

    @Override
    public GenericItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_list, parent, false);
        return new GenericItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final GenericItemViewHolder genericItemViewHolder, final int position) {

        genericItemViewHolder.getDescriptionTextView().setText(list.get(position).getReadableDescription());
        if (position % 2 == 0){
            genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundPairItem);
        }else{
            genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundOddItem);
        }

        if (canEdit) {
            genericItemViewHolder.getPrintImageButton().setVisibility(View.GONE);
            genericItemViewHolder.getEditImageButton().setVisibility(View.VISIBLE);
            genericItemViewHolder.getEditImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onClickEditButton(list.get(position));
                }
            });
        } else {
            if (isDialog) {
                genericItemViewHolder.getContainerButtonsView().setVisibility(View.GONE);
            }else {
                genericItemViewHolder.getPrintImageButton().setVisibility(View.VISIBLE);
                genericItemViewHolder.getEditImageButton().setVisibility(View.GONE);
                genericItemViewHolder.getPrintImageButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onClickPrintButton(list.get(position));
                    }
                });
            }
        }

        genericItemViewHolder.getDeleteImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onClickDeleteButton(list.get(position));
            }
        });

        genericItemViewHolder.getItemWholeLinearLayout().setOnClickListener(new View.OnClickListener() {
            @DebugLog
            @Override
            public void onClick(View v) {
                mPresenter.onClickItem(list.get(position));
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @DebugLog
    public void showNewDataSet(List<? extends BaseModel> listParam) {
        list = listParam;
        notifyDataSetChanged();
    }

    @DebugLog
    public void showMoreDataSet(List<? extends BaseModel> listParam) {
        List aux = new ArrayList<>();
        aux.addAll(listParam);
        list.addAll(aux);
        notifyDataSetChanged();
    }

    @DebugLog
    public void clearDataSet() {
        list = new ArrayList<>();
        notifyDataSetChanged();
    }
}