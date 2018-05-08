package controllers;

import models.manager.InvoiceDetailPurityManager;
import models.manager.impl.InvoiceDetailPurityManagerImpl;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetailPurities
{
    private static InvoiceDetailPurityManager invoiceDetailPurityManager = new InvoiceDetailPurityManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return invoiceDetailPurityManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return invoiceDetailPurityManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return invoiceDetailPurityManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return invoiceDetailPurityManager.findById(id);
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        return invoiceDetailPurityManager.findAll(index, size);
    }
}
