//import models.*;
//
//public class Proof {
//
//    @Override
//    public Result buyHarvestsAndCoffe()
//    {
//        float monto;
//        InvoiceDetailPurity invoiceDetailPurity;
//        JsonNode json = request().body().asJson();
//        if(json == null)
//            return Response.requiredJson();
//
//        JsonNode idprovider = json.get("idProvider");
//        Long idProvider;
//        if (idprovider == null) {
//            JsonNode identificationDocProvider = json.get("identificationDocProvider");
//            if(identificationDocProvider == null){
//                return Response.requiredParameter("identificationDocProvider or idProvider");
//            }else{
//                idProvider = providerDao.getByIdentificationDoc(identificationDocProvider.asText()).getIdProvider();
//            }
//        }else{
//            idProvider = idprovider.asLong();
//        }
//        JsonNode itemtypes = json.get("itemtypes");
//        if (itemtypes == null)
//            return Response.requiredParameter("itemtypes");
//
//        JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
//        if (nameReceived== null)
//            return Response.requiredParameter("nameReceivedInvoiceDetail");
//
//        JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
//        if (nameDelivered==  null)
//            return Response.requiredParameter("nameDeliveredInvoiceDetail");
//
//        JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");;
//        if (startDate==  null)
//            return Response.requiredParameter("startDateInvoiceDetail");
//
//        JsonNode note = json.get("note");
//
//        JsonNode freigh = json.get("freigh");
//        if (freigh==  null)
//            return Response.requiredParameter("freigh");
//
//        JsonNode buyOption = json.get("buyOption");
//        if (buyOption==  null)
//            return Response.requiredParameter("buyOption");
//
//        if(buyOption.asInt() !=1 && buyOption.asInt() != 2)
//            return Response.message("buyOption: 1 for buy Harvests And 2 for buy Coffe");
//
//
//
//        Invoice openInvoice = null;
//
//        for (JsonNode itemtypeAux : itemtypes) {
//
//
//            JsonNode Amount = itemtypeAux.get("amount");
//            if (Amount == null)
//                return Response.requiredParameter("amount");
//
//            JsonNode idItemtype = itemtypeAux.get("idItemType");
//            if (idItemtype == null)
//                return Response.requiredParameter("idItemType");
//
//            ItemType itemType = itemTypeDao.findById(idItemtype.asLong());
//
//            InvoiceDetail invoiceDetail = new InvoiceDetail();
//            invoiceDetail.setItemType(itemType);
//
//            if (buyOption.asInt() == 1)
//            {
//
//                JsonNode idLot = json.get("idLot");
//                if (idLot == null)
//                    return Response.requiredParameter("idLot");
//
//                Lot lot = lotDao.findById(idLot.asLong());
//
//                invoiceDetail.setLot(lot);
//                invoiceDetail.setPriceItemTypeByLot(lot.getPrice_lot());
//                monto = Amount.asInt() * lot.getPrice_lot();
//            } else
//            {
//                JsonNode price = itemtypeAux.get("price");
//                if (price == null)
//                    return Response.requiredParameter("price");
//
//                invoiceDetail.setCostItemType(price.asDouble());
//
//                JsonNode id_store = itemtypeAux.get("id_store");
//                if (id_store == null)
//                    return Response.requiredParameter("id_store");
//
//                invoiceDetail.setStore(storeDao.findById(id_store.asLong()));
//
//                monto = Amount.asInt() * (float) price.asDouble();
//
//                invoiceDetail.setPriceItemTypeByLot((float) 0.00);
//
//                /*
//                comentado por el nuevo caculo que viene en caos de compra
//                monto = Amount.asInt() * itemType.getCostItemType();
//
//                int Discount = (-1)*Math.round((monto*puritys.getDiscountRatePurity())/100);
//                invoiceDetailPurity.setDiscountRatePurity(Discount);
//
//                monto = monto+Discount;*/
//            }
//
//            invoiceDetail.setAmountInvoiceDetail(Amount.floatValue());
//            invoiceDetail.setFreightInvoiceDetail(freigh.asBoolean());
//            invoiceDetail.setNameDeliveredInvoiceDetail(nameDelivered.asText());
//            invoiceDetail.setNameReceivedInvoiceDetail(nameReceived.asText());
//            invoiceDetail.setNoteInvoiceDetail(note.asText());
//
//            DateTime startDatetime;
//            startDatetime = Request.dateTimeFormatter.parseDateTime(startDate.asText());
//
//
//            invoiceDetail.setStartDateInvoiceDetail(startDatetime);
//
//            List<Invoice> invoices = invoiceDao.getOpenByProviderId(idProvider);
//
//            if (!invoices.isEmpty() && invoices.get(0).getStartDateInvoice().toString("yyyy-MM-dd")
//                    .equals(startDatetime.toString("yyyy-MM-dd"))) {
//                openInvoice = invoices.get(0);
//            } else {
//                openInvoice = new Invoice();
//                openInvoice.setStartDateInvoice(startDatetime);
//                openInvoice.setClosedDateInvoice(startDatetime);
//                openInvoice.setProvider(providerDao.findById(idProvider));
//            }
//
//
//            openInvoice.setTotalInvoice(monto + openInvoice.getTotalInvoice());
//
//            List<InvoiceDetail> invoiceDetails = openInvoice.getInvoiceDetails();
//
//            invoiceDetails.add(invoiceDetail);
//            openInvoice.setInvoiceDetails(invoiceDetails);
//
//
//            if (!invoices.isEmpty() && invoices.get(0).getStartDateInvoice().toString("yyyy-MM-dd")
//                    .equals(startDatetime.toString("yyyy-MM-dd")))
//            {
//                openInvoice = invoiceDao.update(openInvoice);
//            }
//            else
//            {
//                openInvoice = invoiceDao.create(openInvoice);
//            }
//
//            invoiceDetail.setInvoice(openInvoice);
//            invoiceDetail = invoiceDetailDao.create(invoiceDetail);
//
//            if(buyOption.asInt() == 2)
//            {
//
//                JsonNode purities = itemtypeAux.get("purities");
//                if (purities == null)
//                    return Response.requiredParameter("purities");
//                for(JsonNode purity : purities)
//                {
//                    invoiceDetailPurity = new InvoiceDetailPurity();
//                    JsonNode idPurity = purity.get("idPurity");
//                    if (idPurity == null)
//                        return Response.requiredParameter("idPurity");
//
//                    JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
//                    if (valueRateInvoiceDetailPurity == null)
//                        return Response.requiredParameter("valueRateInvoiceDetailPurity");
//
//                    Purity puritys = purityDao.findById(idPurity.asLong());
//
//                    invoiceDetailPurity.setPurity(puritys);
//                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.asInt());
//                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
//                    invoiceDetailPurityDao.create(invoiceDetailPurity);
//                }
//            }
//
//        }
//
//        openInvoice.setTotalInvoice(invoiceDao.calcularTotalInvoice(openInvoice.getIdInvoice()));
//        invoiceDao.update(openInvoice);
//        return Response.createdEntity(Json.toJson(openInvoice));
//    }
