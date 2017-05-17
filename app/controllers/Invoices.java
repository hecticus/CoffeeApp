package controllers;

import models.manager.InvoiceManager;
import models.manager.impl.InvoiceManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public class Invoices
{
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

    public Result findAll(Integer index, Integer size) {
        return invoiceManager.findAll(index, size);
    }

    public Result getByDateByTypeProvider(String date, Integer typeProvider){return invoiceManager.getByDateByTypeProvider(date,typeProvider);}

    public Result  getByDateByProviderId(String date, Long providerId){return invoiceManager.getByDateByProviderId(date,providerId);}

    public Result getOpenByProviderId(Long providerId){return invoiceManager.getOpenByProviderId(providerId);}
}
