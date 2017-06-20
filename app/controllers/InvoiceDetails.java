package controllers;

import models.Security.HSecurity;
import models.manager.InvoiceDetailManager;
import models.manager.impl.InvoiceDetailManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetails {
    private static InvoiceDetailManager invoiceDetailManager = new InvoiceDetailManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return invoiceDetailManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return invoiceDetailManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return invoiceDetailManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return invoiceDetailManager.findById(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return invoiceDetailManager.findAll(index, size);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAllByIdInvoice(Long IdInvoice){ return invoiceDetailManager.findAllByIdInvoice(IdInvoice);}
}
