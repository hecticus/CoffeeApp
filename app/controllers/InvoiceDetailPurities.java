package controllers;

import models.manager.InvoiceDetailPurityManager;
import models.manager.impl.InvoiceDetailPurityManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetailPurities
{
    private static InvoiceDetailPurityManager invoiceDetailPurityManager = new InvoiceDetailPurityManagerImpl();

    public Result create() {
        return invoiceDetailPurityManager.create();
    }

    public Result update() {
        return invoiceDetailPurityManager.update();
    }

    public Result delete(Long id) {
        return invoiceDetailPurityManager.delete(id);
    }

    public Result findById(Long id) {
        return invoiceDetailPurityManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return invoiceDetailPurityManager.findAll(index, size);
    }
}
