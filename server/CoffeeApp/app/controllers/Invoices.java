package controllers;

import models.Security.HSecurity;
import models.manager.InvoiceManager;
import models.manager.impl.InvoiceManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public class Invoices
{
    private static InvoiceManager invoiceManager = new InvoiceManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return invoiceManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return invoiceManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return invoiceManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return invoiceManager.findById(id);
    }

   /* @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return invoiceManager.findAll(index, size);
    }*/

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize){return invoiceManager.getByDateByTypeProvider(date,typeProvider,pageIndex,pagesize);}

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result  getByDateByProviderId(String date, Long providerId){return invoiceManager.getByDateByProviderId(date,providerId);}

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getOpenByProviderId(Long providerId){return invoiceManager.getOpenByProviderId(providerId);}

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Pager pager, String sort, String collection) {
        return invoiceManager.findAll(pager.index, pager.size, sort, collection);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return invoiceManager.findAllSearch(name, pager.index, pager.size, sort, collection);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result preCreate() {
        return invoiceManager.preCreate();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result buyHarvestsAndCoffe(){return invoiceManager.buyHarvestsAndCoffe();}
}
