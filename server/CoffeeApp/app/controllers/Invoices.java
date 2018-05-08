package controllers;

import models.manager.InvoiceManager;
import models.manager.impl.InvoiceManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 25/04/17.
 */
public class Invoices {
    private static InvoiceManager invoiceManager = new InvoiceManagerImpl();

    @CoffeAppsecurity
    public    Result create() {
        return invoiceManager.create();
    }

    @CoffeAppsecurity
    public  Result update() {
        return invoiceManager.update();
    }

    @CoffeAppsecurity
    public  Result delete(Long id) {
        return invoiceManager.delete(id);
    }

    @CoffeAppsecurity
    public  Result findById(Long id) {
        return invoiceManager.findById(id);
    }

   /* @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    @CoffeAppsecurity
    public  Result findAll(Integer index, Integer size) {
        return invoiceManager.findAll(index, size);
    } */

    @CoffeAppsecurity
    public  Result getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize){return invoiceManager.getByDateByTypeProvider(date,typeProvider,pageIndex,pagesize);}

    @CoffeAppsecurity
    public  Result  getByDateByProviderId(String date, Long providerId){return invoiceManager.getByDateByProviderId(date,providerId);}

    @CoffeAppsecurity
    public  Result getOpenByProviderId(Long providerId){return invoiceManager.getOpenByProviderId(providerId);}

    @CoffeAppsecurity
    public  Result findAll(Pager pager, String sort, String collection) {
        return invoiceManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public  Result findAllSearch(String name, Pager pager, String sort, String collection,Integer id_provider,Integer id_providertype,String startDate, String endDate) {
        return invoiceManager.findAllSearch(name, pager.index, pager.size, sort, collection,id_provider,id_providertype,startDate,endDate);
    }

    @CoffeAppsecurity
    public  Result preCreate() {
        return invoiceManager.preCreate();
    }

    @CoffeAppsecurity
    public  Result buyHarvestsAndCoffe(){return invoiceManager.buyHarvestsAndCoffe();}

    @CoffeAppsecurity
    public  Result updateBuyHarvestsAndCoffe() {
        return invoiceManager.updateBuyHarvestsAndCoffe();
    }

    @CoffeAppsecurity
    public  Result createReceipt(Long idInvoice)  {
        return invoiceManager.createReceipt(idInvoice);
    }
}
