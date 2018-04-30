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

//@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return invoiceDetailPurityManager.create();
    }

//@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return invoiceDetailPurityManager.update();
    }

//@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return invoiceDetailPurityManager.delete(id);
    }

//@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return invoiceDetailPurityManager.findById(id);
    }

//@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return invoiceDetailPurityManager.findAll(index, size);
    }
}
