package controllers;

import models.manager.InvoiceDetailManager;
import models.manager.impl.InvoiceDetailManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetails {
    private static InvoiceDetailManager invoiceDetailManager = new InvoiceDetailManagerImpl();

    public Result create() {
        return invoiceDetailManager.create();
    }

    public Result update() {
        return invoiceDetailManager.update();
    }

    public Result delete(Long id) {
        return invoiceDetailManager.delete(id);
    }

    public Result findById(Long id) {
        return invoiceDetailManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return invoiceDetailManager.findAll(index, size);
    }

    public Result findAllByIdInvoice(Long IdInvoice){ return invoiceDetailManager.findAllByIdInvoice(IdInvoice);}
}
