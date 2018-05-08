package controllers;

import models.manager.InvoiceDetailManager;
import models.manager.impl.InvoiceDetailManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetails {
    private static InvoiceDetailManager invoiceDetailManager = new InvoiceDetailManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return invoiceDetailManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return invoiceDetailManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return invoiceDetailManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return invoiceDetailManager.findById(id);
    }

  // @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
  /*  public Result findAll(Integer index, Integer size) {
        return invoiceDetailManager.findAll(index, size);
    }*/

    @CoffeAppsecurity
    public Result findAllByIdInvoice(Long IdInvoice){ return invoiceDetailManager.findAllByIdInvoice(IdInvoice);}

//    @HSecurity
    public Result findAll(Pager pager, String sort, String collection) {
        return invoiceDetailManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return invoiceDetailManager.findAllSearch(name, pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result preCreate() {
        return invoiceDetailManager.preCreate();
    }

    @CoffeAppsecurity
    public Result deleteAllByIdInvoiceAndDate( Long IdInvoice, String  date)
    {
            return invoiceDetailManager.deleteAllByIdInvoiceAndDate(IdInvoice,date);
    }
}
