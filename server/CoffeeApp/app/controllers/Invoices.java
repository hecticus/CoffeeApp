package controllers;

import models.manager.InvoiceManager;
import models.manager.impl.InvoiceManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public class Invoices {
    private static InvoiceManager invoiceManager = new InvoiceManagerImpl();

    public Result create() {
        return invoiceManager.create();
    }

    public Result update() {
        return invoiceManager.update();
    }

    public Result delete(Long id) {
        return invoiceManager.delete(id);
    }

    public Result findById(Long id) {
        return invoiceManager.findById(id);
    }

   /* @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return invoiceManager.findAll(index, size);
    } */

    public Result getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize){return invoiceManager.getByDateByTypeProvider(date,typeProvider,pageIndex,pagesize);}

    public Result  getByDateByProviderId(String date, Long providerId){return invoiceManager.getByDateByProviderId(date,providerId);}

    public Result getOpenByProviderId(Long providerId){return invoiceManager.getOpenByProviderId(providerId);}

    public Result findAll(Pager pager, String sort, String collection) {
        return invoiceManager.findAll(pager.index, pager.size, sort, collection);
    }

    public Result findAllSearch(String name, Pager pager, String sort, String collection,Integer id_provider,Integer id_providertype,String startDate, String endDate) {
        return invoiceManager.findAllSearch(name, pager.index, pager.size, sort, collection,id_provider,id_providertype,startDate,endDate);
    }

    public Result preCreate() {
        return invoiceManager.preCreate();
    }

    public Result buyHarvestsAndCoffe(){return invoiceManager.buyHarvestsAndCoffe();}

    public Result updateBuyHarvestsAndCoffe() {
        return invoiceManager.updateBuyHarvestsAndCoffe();
    }

    public Result createReceipt(Long idInvoice)  {
        return invoiceManager.createReceipt(idInvoice);
    }
}
