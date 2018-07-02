package com.hecticus.eleta;

public class borrar {
/*# Routes
# This file defines all application routes (Higher priority routes first)
# ~~~~
# An example controller showing a sample home page
    GET     /                                   controllers.HomeController.index

# An example controller showing how to write asynchronous code
    GET     /message                            controllers.AsyncController.message

# Map static resources from the /public folder to the /assets URL path
    GET     /assets/*file                       controllers.Assets.versioned(path="/public", file: Asset)
#GET     /assets/*file               controllers.Assets.at(path="/public", file)

# GET
# POST
# PUT
# DELETE


###############################################################################
#·······                     Authentication                         ··········#
###############################################################################
POST    /oauth/token                                                            security.authentication.oauth2.PasswordGrant.token()
POST    /oauth/revokeToken                                                      security.authentication.oauth2.PasswordGrant.revokeToken()
#POST    /oauth/auth                                                            security.authentication.oauth2.ImplicitGrant.auth()

###############################################################################
#·······                     User                                   ··········#
###############################################################################
GET     /user                                                                   controllers.Users.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, firstName: String ?= null, lastName: String ?= null,  deleted: Boolean ?= false )
GET     /user/:id                                                               controllers.Users.findById(id : Long)
POST    /user                                                                   controllers.Users.create()
PUT     /user/:id                                                               controllers.Users.update(id : Long)
DELETE  /user/:id                                                               controllers.Users.delete(id : Long)

###############################################################################
#·······                     Roles                                  ··········#
###############################################################################
GET     /roles                                                                   security.controllers.Roles.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, deleted: Boolean ?= false )
GET     /roles/:id                                                               security.controllers.Roles.findById(id : Long)
POST    /roles                                                                   security.controllers.Roles.create()
PUT     /roles/:id                                                               security.controllers.Roles.update(id : Long)
DELETE  /roles/:id                                                               security.controllers.Roles.delete(id : Long)

###############################################################################
#·······                     Group                                  ··········#
###############################################################################
GET     /group                                                                   security.controllers.Groups.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, deleted: Boolean ?= false )
GET     /group/:id                                                               security.controllers.Groups.findById(id : Long)
POST    /group                                                                   security.controllers.Groups.create()
PUT     /group/:id                                                               security.controllers.Groups.update(id : Long)
DELETE  /group/:id                                                               security.controllers.Groups.delete(id : Long)

###############################################################################
#·······                        Farm                                ··········#
###############################################################################
#GET     /farm                                                                   controllers.Farms.findAll(index: Integer ?= null, size: Integer ?= null, collection: String ?= null, name: String ?= null,  sort: String ?= null, status: Long ?= 0L, deleted: Boolean ?= false)
#GET     /farm/search                                                            controllers.Farms.findAll(index: Integer ?= null, size: Integer ?= null, collection: String ?= null, name: String ?= null,  sort: String ?= null, status: Long ?= 0L, deleted: Boolean ?= false)
#GET     /farm/:id                                                               controllers.Farms.findById(id : Long)


# New path add by sm21
GET     /farm                                                                   controllers.Farms.findAll(index: Integer ?= null, size: Integer ?= null, collection: String ?= null, nameFarm: String ?= null,  sort: String ?= null, statusFarm: Long ?= 0L, deleted: Boolean ?= false)
GET     /farm/:id                                                               controllers.Farms.findById(id : Long)
POST    /farm                                                                   controllers.Farms.create()
POST    /farm/delete                                                            controllers.Farms.deletes()
PUT     /farm/:id                                                               controllers.Farms.update(id : Long)
DELETE  /farm/:id                                                               controllers.Farms.delete(id : Long)
#
################################################################################
##·······                      Invoice                               ··········#
################################################################################
#GET     /invoice                                                                controllers.Invoices.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,sort: String ?= null, providerId: Long ?= 0L, typeProvider: Long ?= 0L, startDate: String ?= null, endDate: String ?= null, status: Long ?= 0L, deleted: Boolean ?= false)
#GET     /invoice/search                                                         controllers.Invoices.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,sort: String ?= null, ProviderId: Long ?= 0L, typeProvider: Long ?= 0L, startDate: String ?= null, endDate: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /invoice/getByDateByProviderId/:ProviderId                              controllers.Invoices.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,sort: String ?= null, ProviderId: Long      , typeProvider: Long ?= 0L, startDate: String ?= null, endDate: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /invoice/getByDateByProviderId/:date/:ProviderId                        controllers.Invoices.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,sort: String ?= null, ProviderId: Long      , typeProvider: Long ?= 0L, date: String             , endDate: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /invoice/getByDateByTypeProvider/:date/:typeProvider/:pageindex/:pagesize controllers.Invoices.findAll( pageindex: Integer      , pagesize: Integer        , collection: String ?= null,sort: String ?= null, ProviderId: Long ?= 0L, typeProvider: Long      , date: String             , endDate: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /invoice/:id                                                            controllers.Invoices.findById(id : Long)
##GET     /invoice/createReceipt/:id                                              controllers.Invoices.createReceipt(id : Long)
#POST    /invoice                                                                controllers.Invoices.create()
##POST    /invoice/buyHarvestsAndCoffe                                            controllers.Invoices.buyHarvestsAndCoffe()
##PUT     /invoice/buyHarvestsAndCoffe                                            controllers.Invoices.updateBuyHarvestsAndCoffe()
#PUT     /invoice                                                                controllers.Invoices.update()
#DELETE  /invoice/:id                                                            controllers.Invoices.delete(id : Long)


#New
GET     /invoice                                                                controllers.Invoices.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,sort: String ?= null, providerId: Long ?= 0L, typeProvider: Long ?= 0L, startDate: String ?= null, endDate: String ?= null, statusInvoice: Long ?= 0L, deleted: Boolean ?= false)
GET     /invoice/:id                                                            controllers.Invoices.findById(id : Long)
#GET     /invoice/createReceipt/:id                                              controllers.Invoices.createReceipt(id : Long)
#POST    /invoice/buyHarvestsAndCoffe                                            controllers.Invoices.buyHarvestsAndCoffe()
#PUT     /invoice/buyHarvestsAndCoffe                                            controllers.Invoices.updateBuyHarvestsAndCoffe()
POST    /invoice/delete                                                         controllers.Invoices.deletes()
POST    /invoice                                                                controllers.Invoices.create()
PUT     /invoice/:id                                                            controllers.Invoices.update(id : Long)
DELETE  /invoice/:id                                                            controllers.Invoices.delete(id : Long)


################################################################################
##·······                       InvoiceDetail                        ··········#
################################################################################
#GET     /invoiceDetail                                                          controllers.InvoiceDetails.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,  sort: String ?= null, invoiceId: Long ?= 0L, itemType: Long ?= 0L, lot: Long ?= 0L, store: Long ?= 0L,  nameReceivedInvoiceDetail: String ?= null, startDateInvoiceDetail: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /invoiceDetail/search                                                   controllers.InvoiceDetails.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,  sort: String ?= null, invoiceId: Long ?= 0L, itemType: Long ?= 0L, lot: Long ?= 0L, store: Long ?= 0L,  nameReceivedInvoiceDetail: String ?= null, startDateInvoiceDetail: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /invoiceDetail/new                                                      controllers.InvoiceDetails.preCreate()
#GET     /invoiceDetail/findAllByIdInvoice/:invoiceId                            controllers.InvoiceDetails.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,  sort: String ?= null, invoiceId: Long      , itemType: Long ?= 0L, lot: Long ?= 0L, store: Long ?= 0L,  nameReceivedInvoiceDetail: String ?= null, startDateInvoiceDetail: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /invoiceDetail/:id                                                      controllers.InvoiceDetails.findById(id : Long)
#POST    /invoiceDetail                                                          controllers.InvoiceDetails.create()
#PUT     /invoiceDetail                                                          controllers.InvoiceDetails.update()
#DELETE  /invoiceDetail/:id                                                      controllers.InvoiceDetails.delete(id : Long)
##DELETE  /invoiceDetail/deleteAllByIdInvoiceAndDate/:idInvoice/:date            controllers.InvoiceDetails.deleteAllByIdInvoiceAndDate(idInvoice : Long, date: String)
#New


GET     /invoiceDetail                                                          controllers.InvoiceDetails.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,  sort: String ?= null, invoice: Long ?= 0L, itemType: Long ?= 0L, lot: Long ?= 0L, store: Long ?= 0L,  nameDelivered: String ?= null, nameReceived: String ?= null, startDate: String ?= null,  statusInvoiceDetail: Long ?= 0L, deleted: Boolean ?= false)
GET     /invoiceDetail/:id                                                      controllers.InvoiceDetails.findById(id : Long)
POST    /invoiceDetail                                                          controllers.InvoiceDetails.create()
POST    /invoiceDetail/delete                                                   controllers.InvoiceDetails.deletes()
PUT     /invoiceDetail/:id                                                      controllers.InvoiceDetails.update(id : Long)
DELETE  /invoiceDetail/:id                                                      controllers.InvoiceDetails.delete(id : Long)

################################################################################
##·······              InvoiceDetailPurity                           ··········#
################################################################################
GET     /invoiceDetailPurity                                                    controllers.InvoiceDetailPurities.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,  sort: String ?= null, invoiceDetail: Long ?= 0L, purity: Long ?= 0L, deleted: Boolean ?= false)
GET     /invoiceDetailPurity/:id                                                controllers.InvoiceDetailPurities.findById(id : Long)
POST    /invoiceDetailPurity/delete                                            controllers.InvoiceDetailPurities.deletes()
POST    /invoiceDetailPurity                                                    controllers.InvoiceDetailPurities.create()
PUT     /invoiceDetailPurity/:id                                                controllers.InvoiceDetailPurities.update(id : Long)
DELETE  /invoiceDetailPurity/:id                                                controllers.InvoiceDetailPurities.delete(id : Long)

################################################################################
##·······                       ItemType                             ··········#
################################################################################
#GET     /itemType                                                               controllers.ItemTypes.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, id_providerType: Long ?= 0L, status: Integer?= null, deleted: Boolean ?= false)
#GET     /itemType/search                                                        controllers.ItemTypes.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, id_providerType: Long ?= 0L, status: Integer?= null, deleted: Boolean ?= false)
#GET     /itemType/new                                                           controllers.ItemTypes.preCreate()
#GET     /itemType/getByProviderTypeId/:id_ProviderType/:status                  controllers.ItemTypes.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, id_ProviderType: Long      , status: Integer       , deleted: Boolean ?= false)
#GET     /itemType/getByNameItemType/:NameItemType/:order                        controllers.ItemTypes.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , NameItemType: String, id_providerType: Long ?= 0L, status: Integer?= null, deleted: Boolean ?= false)
#GET     /itemType/:id                                                           controllers.ItemTypes.findById(id : Long)
#POST    /itemType                                                               controllers.ItemTypes.create()
#PUT     /itemType                                                               controllers.ItemTypes.update()
#DELETE  /itemType/:id                                                           controllers.ItemTypes.delete(id : Long)


# todo NEW
GET     /itemType                                                               controllers.ItemTypes.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, nameItemType: String ?= null, providerType: Long ?= 0L, unit: Long ?= 0L, deleted: Boolean ?= false)
GET     /itemType/:id                                                           controllers.ItemTypes.findById(id : Long)
POST    /itemType                                                               controllers.ItemTypes.create()
POST    /itemType                                                               controllers.ItemTypes.deletes()
PUT     /itemType/:id                                                           controllers.ItemTypes.update(id : Long)
DELETE  /itemType/:id                                                           controllers.ItemTypes.delete(id : Long)

################################################################################
##·······                          lot                               ··········#
################################################################################
#GET     /lot                                                                    controllers.Lots.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, idFarm:  Long ?= 0L, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /lot/new                                                                controllers.Lots.preCreate()
#GET     /lot/search                                                             controllers.Lots.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, idFarm:  Long ?= 0L, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /lot/getByNameLot/:NameLot/:order                                       controllers.Lots.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , NameLot: String     , idFarm:  Long ?= 0L, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /lot/getByStatusLot/:StatusLot/:order                                   controllers.Lots.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , name: String ?= null, idFarm:  Long ?= 0L, StatusLot: Integer     , deleted: Boolean ?= false)
#GET     /lot/:id                                                                controllers.Lots.findById(id : Long)
#POST    /lot                                                                    controllers.Lots.create()
#PUT     /lot                                                                    controllers.Lots.update()
#POST    /lot/deletes                                                            controllers.Lots.deletes()
#DELETE  /lot/:id                                                                controllers.Lots.delete(id : Long)


#NEW
GET     /lot                                                                    controllers.Lots.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, nameLot: String ?= null, farm:  Long ?= 0L, statusLot: Long ?= 0L, deleted: Boolean ?= false)
GET     /lot/:id                                                                controllers.Lots.findById(id : Long)
POST    /lot                                                                    controllers.Lots.create()
POST    /lot/delete                                                             controllers.Lots.deletes()
PUT     /lot/:id                                                                controllers.Lots.update(id : Long)
DELETE  /lot/:id                                                                controllers.Lots.delete(id : Long)

################################################################################
##·······                          Provider                          ··········#
################################################################################
#GET     /provider                                                               controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, idProviderType: Long ?= 0L, identificationDocProvider: String ?= null, addressProvider: String ?= null, phoneNumberProvider: String ?= null, emailProviderall: String ?= null, contactNameProvider: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /provider/new                                                           controllers.Providers.preCreate()
#GET     /provider/search                                                        controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, idProviderType: Long ?= 0L, identificationDocProvider: String ?= null, addressProvider: String ?= null, phoneNumberProvider: String ?= null, emailProviderall: String ?= null, contactNameProvider: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /provider/getProvidersByName/:name/:order                               controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , name: String        , idProviderType: Long ?= 0L, identificationDocProvider: String ?= null, addressProvider: String ?= null, phoneNumberProvider: String ?= null, emailProviderall: String ?= null, contactNameProvider: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /provider/getByIdentificationDoc/:IdentificationDoc                     controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, idProviderType: Long ?= 0L, IdentificationDoc: String                , addressProvider: String ?= null, phoneNumberProvider: String ?= null, emailProviderall: String ?= null, contactNameProvider: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /provider/getByNameDocByTypeProvider/:nameDoc/:id_providertype/:order   controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , name: String ?= null, id_providertype: Long     , nameDoc: String                          , addressProvider: String ?= null, phoneNumberProvider: String ?= null, emailProviderall: String ?= null, contactNameProvider: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /provider/getByTypeProvider/:id_providertype/:order                     controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , name: String ?= null, id_providertype: Long     , identificationDocProvider: String ?= null, addressProvider: String ?= null, phoneNumberProvider: String ?= null, emailProviderall: String ?= null, contactNameProvider: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /provider/:index/:size                                                  controllers.Providers.findAll( index: Integer            , size: Integer            , collection: String ?= null, sort: String ?= null, name: String ?= null, idProviderType: Long ?= 0L, identificationDocProvider: String ?= null, addressProvider: String ?= null, phoneNumberProvider: String ?= null, emailProviderall: String ?= null, contactNameProvider: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /provider/:id                                                           controllers.Providers.findById(id : Long)
#POST    /provider                                                               controllers.Providers.create()
#POST    /provider/deletes                                                       controllers.Providers.deletes()
#POST    /provider/uploadPhotoProvider                                           controllers.Providers.uploadPhotoProvider()
#PUT     /provider                                                               controllers.Providers.update()
#DELETE  /provider/:id                                                           controllers.Providers.delete(id : Long)


#NEW
GET     /provider                                                               controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, nameProvider: String ?= null, providerType: Long ?= 0L, nitProvider: String ?= null, addressProvider: String ?= null, numberProvider: String ?= null, emailProvider: String ?= null, contactNameProvider: String ?= null, statusProvider: Long ?= 0L, deleted: Boolean ?= false)
GET     /provider/:id                                                           controllers.Providers.findById(id : Long)
POST    /provider                                                               controllers.Providers.create()
POST    /provider/delete                                                        controllers.Providers.deletes()
#POST    /provider/uploadPhotoProvider                                           controllers.Providers.uploadPhotoProvider()
PUT     /provider/:id                                                                controllers.Providers.update(id : Long)
DELETE  /provider/:id                                                           controllers.Providers.delete(id : Long)

################################################################################
##·······                 ProviderType                               ··········#
################################################################################
#GET     /providerType                                                           controllers.ProviderTypes.findAll( index: Integer ?= null, size: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /providerType/findAll/:index/:size                                      controllers.ProviderTypes.findAll( index: Integer        , size: Integer        , collection: String ?= null, sort: String ?= null, name: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /providerType/getProviderTypesByName/:name/:order                       controllers.ProviderTypes.findAll( index: Integer ?= null, size: Integer ?= null, collection: String ?= null, order: String       , name: String        ,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /providerType/:id                                                       controllers.ProviderTypes.findById(id : Long)


##New creates by sm21
GET     /providerType                                                           controllers.ProviderTypes.findAll( index: Integer ?= null, size: Integer ?= null, collection: String ?= null, sort: String ?= null, nameProviderType: String ?= null, deleted: Boolean ?= false)
GET     /providerType/:id                                                       controllers.ProviderTypes.findById(id : Long)
POST    /providerType                                                           controllers.ProviderTypes.create()
POST    /providerType/delete                                                    controllers.ProviderTypes.deletes()
PUT     /providerType/:id                                                       controllers.ProviderTypes.update(id : Long)
DELETE  /providerType/:id                                                       controllers.ProviderTypes.delete(id : Long)

################################################################################
##·······                        Purity                              ··········#
################################################################################
#GET     /purity                                                                 controllers.Purities.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /purity/new                                                             controllers.Purities.preCreate()
#GET     /purity/search                                                          controllers.Purities.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /purity/getByNamePurity/:NamePurity/:order                              controllers.Purities.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , NamePurity: String  ,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /purity/getByStatusPurity/:StatusPurity/:order                          controllers.Purities.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , name: String ?= null,  StatusPurity: Integer  , deleted: Boolean ?= false)
#GET     /purity/:id                                                             controllers.Purities.findById(id : Long)
#PUT     /purity                                                                 controllers.Purities.update()
#POST    /purity                                                                 controllers.Purities.create()
#DELETE  /purity/:id                                                             controllers.Purities.delete(id : Long)


#New
GET     /purity                                                                 controllers.Purities.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, namePurity: String ?= null, deleted: Boolean ?= false)
GET     /purity/:id                                                             controllers.Purities.findById(id : Long)
POST    /purity                                                                 controllers.Purities.create()
POST    /purity/delete                                                          controllers.Purities.deletes()
PUT     /purity/:id                                                             controllers.Purities.update(id : Long)
DELETE  /purity/:id                                                             controllers.Purities.delete(id : Long)

################################################################################
##·······                     Store                                  ··········#
################################################################################
#GET     /store                                                                  controllers.Stores.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /store/new                                                              controllers.Stores.preCreate()
#GET     /store/search                                                           controllers.Stores.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null, status: Integer ?= null, deleted: Boolean ?= false)
#GET     /store/getByStatusStore/:StatusStore/:order                             controllers.Stores.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, order: String       , name: String ?= null, StatusStore: Integer   , deleted: Boolean ?= false)
#GET     /store/:id                                                              controllers.Stores.findById(id : Long)
#POST    /store                                                                  controllers.Stores.create()
#PUT     /store                                                                  controllers.Stores.update()
#DELETE  /store/:id                                                              controllers.Stores.delete(id : Long)


#New
GET     /store                                                                  controllers.Stores.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, nameStore: String ?= null, statusStore: Long ?= 0L, deleted: Boolean ?= false)
GET     /store/:id                                                              controllers.Stores.findById(id : Long)
POST    /store/delete                                                           controllers.Stores.deletes()
POST    /store                                                                  controllers.Stores.create()
PUT     /store/:id                                                              controllers.Stores.update(id : Long)
DELETE  /store/:id                                                              controllers.Stores.delete(id : Long)

################################################################################
##·······                       Unit                                 ··········#
################################################################################
#GET     /unit                                                                   controllers.Units.findAll( index: Integer ?= null, size: Integer ?= null, collection: String ?= null, sort: String ?= null, name: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /unit/findAll/:index/:size                                              controllers.Units.findAll( index: Integer        , size: Integer        , collection: String ?= null, sort: String ?= null, name: String ?= null,  status: Integer ?= null, deleted: Boolean ?= false)
#GET     /unit/:id                                                               controllers.Units.findById(id : Long)
#PUT     /unit                                                                   controllers.Units.update()
#POST    /unit                                                                   controllers.Units.create()
#DELETE  /unit/:id                                                               controllers.Units.delete(id : Long)


#NEW
GET     /unit                                                                   controllers.Units.findAll( index: Integer ?= null, size: Integer ?= null, collection: String ?= null, sort: String ?= null, nameUnit: String ?= null, deleted: Boolean ?= false)
GET     /unit/:id                                                               controllers.Units.findById(id : Long)
POST    /unit/delete                                                            controllers.Units.deletes()
POST    /unit                                                                   controllers.Units.create()
PUT     /unit/:id                                                               controllers.Units.update(id : Long)
DELETE  /unit/:id                                                               controllers.Units.delete(id : Long)
*/

}
