package models.manager;

import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public interface InvoiceManager {


    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

  //  public Result findAll(Integer index, Integer size);

    public Result getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize);

    public Result getByDateByProviderId(String date, Long providerId);
    public Result  getOpenByProviderId(Long providerId);


    Result findAll(Integer index, Integer size, String sort, String collection);

    Result findAllSearch(String name, Integer index, Integer size, String sort, String collection,Integer id_provider,Integer id_providertype,String startDate, String endDate);

    Result preCreate();

    Result buyHarvestsAndCoffe();

    Result updateBuyHarvestsAndCoffe();

    public Result createReceipt(Long idInvoice);
}
