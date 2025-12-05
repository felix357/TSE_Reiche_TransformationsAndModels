<?xml version="1.0" encoding="ASCII"?>
<java:JavaRoot xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:java="http://www.example.org/java" xmlns:types="http://www.example.org/java/types">
  <primitivetypes id="_kPpArfUWEe-xz_jKIkr2Qg" name="boolean"/>
  <primitivetypes id="_kPpArvUWEe-xz_jKIkr2Qg" name="int" kind="BYTE"/>
  <primitivetypes id="_kPpAr_UWEe-xz_jKIkr2Qg" name="short" kind="SHORT"/>
  <primitivetypes id="_kPpAsPUWEe-xz_jKIkr2Qg" name="int" kind="INT"/>
  <primitivetypes id="_kPpAsfUWEe-xz_jKIkr2Qg" name="long" kind="LONG"/>
  <primitivetypes id="_kPpAsvUWEe-xz_jKIkr2Qg" name="char" kind="CHAR"/>
  <primitivetypes id="_kPpAs_UWEe-xz_jKIkr2Qg" name="float" kind="FLOAT"/>
  <primitivetypes id="_kPpAtPUWEe-xz_jKIkr2Qg" name="double" kind="DOUBLE"/>
  <primitivetypes id="_kPpAtfUWEe-xz_jKIkr2Qg" name="String" kind="STRING"/>
  <collectiontypes id="_kPqOwPUWEe-xz_jKIkr2Qg" name="Collection&lt;OrderEntryTO>" type="//@package/@subpackage.1/@classorinterface.8"/>
  <collectiontypes id="_kPqOwfUWEe-xz_jKIkr2Qg" name="Collection&lt;ProductAmountTO>" type="//@package/@subpackage.1/@classorinterface.15"/>
  <collectiontypes id="_kPqOwvUWEe-xz_jKIkr2Qg" name="Collection&lt;ProductWithStockItemTO>" type="//@package/@subpackage.1/@classorinterface.11"/>
  <collectiontypes id="_kPqOw_UWEe-xz_jKIkr2Qg" name="Collection&lt;ProductOrder>" type="//@package/@subpackage.1/@classorinterface.21"/>
  <collectiontypes id="_kPqOxPUWEe-xz_jKIkr2Qg" name="Collection&lt;StockItem>" type="//@package/@subpackage.1/@classorinterface.27"/>
  <collectiontypes id="_kPqOxfUWEe-xz_jKIkr2Qg" name="Collection&lt;SupplierTO>" type="//@package/@subpackage.1/@classorinterface.12"/>
  <collectiontypes id="_kPqOxvUWEe-xz_jKIkr2Qg" name="Collection&lt;Store>" type="//@package/@subpackage.1/@classorinterface.18"/>
  <collectiontypes id="_kPqOx_UWEe-xz_jKIkr2Qg" name="Collection&lt;Product>" type="//@package/@subpackage.1/@classorinterface.24"/>
  <collectiontypes id="_kPq16PUWEe-xz_jKIkr2Qg" name="Collection&lt;ProductWithSupplierTO>" type="//@package/@subpackage.1/@classorinterface.13"/>
  <collectiontypes id="_kPq16vUWEe-xz_jKIkr2Qg" name="Collection&lt;ProductWithSupplierAndStockItemTO>" type="//@package/@subpackage.1/@classorinterface.14"/>
  <collectiontypes id="_kPq17PUWEe-xz_jKIkr2Qg" name="Collection&lt;ComplexOrderTO>" type="//@package/@subpackage.1/@classorinterface.7"/>
  <package id="_kPtSEPUWEe-xz_jKIkr2Qg" name="edu.kit.kastel.sdq.coupling.casestudy.cocome">
    <subpackage id="_kPpAqvUWEe-xz_jKIkr2Qg" name="components">
      <classorinterface xsi:type="types:Class" id="_kPrdI_UWEe-xz_jKIkr2Qg" name="DataPersistence" implements="//@package/@subpackage.2/@classorinterface.15">
        <method id="_kPrdJPUWEe-xz_jKIkr2Qg" name="getPersistenceContext" returntype="//@package/@subpackage.1/@classorinterface.9"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPrdJfUWEe-xz_jKIkr2Qg" name="SalesRegisteredEventProvider" implements="//@package/@subpackage.2/@classorinterface.17"/>
      <classorinterface xsi:type="types:Class" id="_kPrdJvUWEe-xz_jKIkr2Qg" name="ApplicationStore" implements="//@package/@subpackage.2/@classorinterface.13 //@package/@subpackage.2/@classorinterface.14">
        <method id="_kPrdJ_UWEe-xz_jKIkr2Qg" name="getStore" returntype="//@package/@subpackage.1/@classorinterface.2"/>
        <method id="_kPrdKPUWEe-xz_jKIkr2Qg" name="getProductsWithLowStock" returntype="//@collectiontypes.2"/>
        <method id="_kPrdKfUWEe-xz_jKIkr2Qg" name="getAllProducts" returntype="//@collectiontypes.8"/>
        <method id="_kPrdKvUWEe-xz_jKIkr2Qg" name="getAllProductsWithOptionalStockItem" returntype="//@collectiontypes.9"/>
        <method id="_kPrdK_UWEe-xz_jKIkr2Qg" name="orderProducts" returntype="//@collectiontypes.10">
          <parameter id="_kPrdLPUWEe-xz_jKIkr2Qg" name="complexOrder" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPrdLfUWEe-xz_jKIkr2Qg" name="getOrder" returntype="//@package/@subpackage.1/@classorinterface.7">
          <parameter id="_kPrdLvUWEe-xz_jKIkr2Qg" name="orderId" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPrdL_UWEe-xz_jKIkr2Qg" name="rollInReceivedOrder">
          <parameter id="_kPrdMPUWEe-xz_jKIkr2Qg" name="complexOrderTO" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPrdMfUWEe-xz_jKIkr2Qg" name="changePrice" returntype="//@package/@subpackage.1/@classorinterface.11">
          <parameter id="_kPrdMvUWEe-xz_jKIkr2Qg" name="stockItemTO" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_kPrdM_UWEe-xz_jKIkr2Qg" name="markProductsUnavailableInStock">
          <parameter id="_kPrdNPUWEe-xz_jKIkr2Qg" name="requiredProductsAndAmount" type="//@package/@subpackage.1/@classorinterface.16"/>
        </method>
        <method id="_kPrdNfUWEe-xz_jKIkr2Qg" name="bookSale">
          <parameter id="_kPrdNvUWEe-xz_jKIkr2Qg" name="sale" type="//@package/@subpackage.1/@classorinterface.17"/>
        </method>
        <method id="_kPrdN_UWEe-xz_jKIkr2Qg" name="getProductWithStockItem" returntype="//@package/@subpackage.1/@classorinterface.11">
          <parameter id="_kPrdOPUWEe-xz_jKIkr2Qg" name="productBarcode" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.15" name="persistenceif"/>
        <field type="//@package/@subpackage.2/@classorinterface.17" name="saleregisteredevent"/>
        <field type="//@package/@subpackage.2/@classorinterface.16" name="storequeryif"/>
        <field type="//@package/@subpackage.2/@classorinterface.7" name="productdispatcherif"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPrdOfUWEe-xz_jKIkr2Qg" name="DataStore" implements="//@package/@subpackage.2/@classorinterface.16">
        <method id="_kPsD8PUWEe-xz_jKIkr2Qg" name="queryStoreById" returntype="//@package/@subpackage.1/@classorinterface.18">
          <parameter id="_kPsD8fUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPsD8vUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsD8_UWEe-xz_jKIkr2Qg" name="queryProducts" returntype="//@collectiontypes.7">
          <parameter id="_kPsD9PUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPsD9fUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsD9vUWEe-xz_jKIkr2Qg" name="queryLowStockItems" returntype="//@collectiontypes.4">
          <parameter id="_kPsD9_UWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPsD-PUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsD-fUWEe-xz_jKIkr2Qg" name="queryLowStockItemsWithRespectToIncomingProducts" returntype="//@collectiontypes.4">
          <parameter id="_kPsD-vUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPsD-_UWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsD_PUWEe-xz_jKIkr2Qg" name="queryAllStockItems" returntype="//@collectiontypes.4">
          <parameter id="_kPsD_fUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPsD_vUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsD__UWEe-xz_jKIkr2Qg" name="queryStockItem" returntype="//@package/@subpackage.1/@classorinterface.27">
          <parameter id="_kPsEAPUWEe-xz_jKIkr2Qg" name="stockId" type="//@primitivetypes.3"/>
          <parameter id="_kPsEAfUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsEAvUWEe-xz_jKIkr2Qg" name="queryStockItemById" returntype="//@package/@subpackage.1/@classorinterface.27">
          <parameter id="_kPsEA_UWEe-xz_jKIkr2Qg" name="stockId" type="//@primitivetypes.3"/>
          <parameter id="_kPsEBPUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsEBfUWEe-xz_jKIkr2Qg" name="queryOrderById" returntype="//@package/@subpackage.1/@classorinterface.21">
          <parameter id="_kPsEBvUWEe-xz_jKIkr2Qg" name="orderId" type="//@primitivetypes.3"/>
          <parameter id="_kPsEB_UWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsECPUWEe-xz_jKIkr2Qg" name="queryProductById" returntype="//@package/@subpackage.1/@classorinterface.24">
          <parameter id="_kPsECfUWEe-xz_jKIkr2Qg" name="productId" type="//@primitivetypes.3"/>
          <parameter id="_kPsECvUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPsEC_UWEe-xz_jKIkr2Qg" name="getStockItems" returntype="//@collectiontypes.4">
          <parameter id="_kPsEDPUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPsEDfUWEe-xz_jKIkr2Qg" name="productId" type="//@primitivetypes.3"/>
          <parameter id="_kPsEDvUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsED_UWEe-xz_jKIkr2Qg" name="GUIStore" implements="//@package/@subpackage.2/@classorinterface.5">
        <method id="_kPsEEPUWEe-xz_jKIkr2Qg" name="getStore" returntype="//@package/@subpackage.1/@classorinterface.2"/>
        <method id="_kPsEEfUWEe-xz_jKIkr2Qg" name="getProductsWithLowStock" returntype="//@collectiontypes.2"/>
        <method id="_kPsEEvUWEe-xz_jKIkr2Qg" name="getAllProducts" returntype="//@collectiontypes.8"/>
        <method id="_kPsEE_UWEe-xz_jKIkr2Qg" name="getAllProductsWithOptionalStockItem" returntype="//@collectiontypes.9"/>
        <method id="_kPsEFPUWEe-xz_jKIkr2Qg" name="orderProducts" returntype="//@collectiontypes.10">
          <parameter id="_kPsEFfUWEe-xz_jKIkr2Qg" name="complexOrder" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPsEFvUWEe-xz_jKIkr2Qg" name="getOrder" returntype="//@package/@subpackage.1/@classorinterface.7">
          <parameter id="_kPsEF_UWEe-xz_jKIkr2Qg" name="orderId" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsEGPUWEe-xz_jKIkr2Qg" name="rollInReceivedOrder">
          <parameter id="_kPsEGfUWEe-xz_jKIkr2Qg" name="complexOrderTO" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPsEGvUWEe-xz_jKIkr2Qg" name="changePrice" returntype="//@package/@subpackage.1/@classorinterface.11">
          <parameter id="_kPsEG_UWEe-xz_jKIkr2Qg" name="stockItemTO" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_kPsEHPUWEe-xz_jKIkr2Qg" name="markProductsUnavailableInStock">
          <parameter id="_kPsEHfUWEe-xz_jKIkr2Qg" name="requiredProductsAndAmount" type="//@package/@subpackage.1/@classorinterface.16"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.13" name="storeif"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsEHvUWEe-xz_jKIkr2Qg" name="CashDesk" implements="//@package/@subpackage.2/@classorinterface.11 //@package/@subpackage.2/@classorinterface.9 //@package/@subpackage.2/@classorinterface.10">
        <method id="_kPsEH_UWEe-xz_jKIkr2Qg" name="readBarcode">
          <parameter id="_kPsEIPUWEe-xz_jKIkr2Qg" name="barcode" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsEIfUWEe-xz_jKIkr2Qg" name="readCardNumber">
          <parameter id="_kPsEIvUWEe-xz_jKIkr2Qg" name="number" type="//@primitivetypes.3"/>
          <parameter id="_kPsEI_UWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsEJPUWEe-xz_jKIkr2Qg" name="startNewSale"/>
        <method id="_kPsEJfUWEe-xz_jKIkr2Qg" name="completeItemRegistration"/>
        <method id="_kPsEJvUWEe-xz_jKIkr2Qg" name="acknowladgeCashPayment" returntype="//@primitivetypes.0">
          <parameter id="_kPsEJ_UWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.14" name="cashdeskconnectorif"/>
        <field type="//@package/@subpackage.2/@classorinterface.8" name="lightdisplayif"/>
        <field type="//@package/@subpackage.2/@classorinterface.12" name="printerif"/>
        <field type="//@package/@subpackage.2/@classorinterface.18" name="billinginteraction"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsEKPUWEe-xz_jKIkr2Qg" name="BarCodeScanner" implements="//@package/@subpackage.2/@classorinterface.3">
        <method id="_kPsEKfUWEe-xz_jKIkr2Qg" name="readBarcode">
          <parameter id="_kPsEKvUWEe-xz_jKIkr2Qg" name="barcode" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.11" name="scannerif"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsEK_UWEe-xz_jKIkr2Qg" name="BarCodeScanner" implements="//@package/@subpackage.2/@classorinterface.3">
        <method id="_kPsrAPUWEe-xz_jKIkr2Qg" name="readBarcode">
          <parameter id="_kPsrAfUWEe-xz_jKIkr2Qg" name="barcode" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.11" name="scannerif"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsrAvUWEe-xz_jKIkr2Qg" name="CardReader" implements="//@package/@subpackage.2/@classorinterface.2">
        <method id="_kPsrA_UWEe-xz_jKIkr2Qg" name="readCardNumber">
          <parameter id="_kPsrBPUWEe-xz_jKIkr2Qg" name="number" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrBfUWEe-xz_jKIkr2Qg" name="readPIN">
          <parameter id="_kPsrBvUWEe-xz_jKIkr2Qg" name="pin" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.9" name="cardreaderif"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsrB_UWEe-xz_jKIkr2Qg" name="CashBox" implements="//@package/@subpackage.2/@classorinterface.4">
        <method id="_kPsrCPUWEe-xz_jKIkr2Qg" name="startNewSale"/>
        <method id="_kPsrCfUWEe-xz_jKIkr2Qg" name="completeItemRegistration"/>
        <method id="_kPsrCvUWEe-xz_jKIkr2Qg" name="acknowladgeCashPayment" returntype="//@primitivetypes.0">
          <parameter id="_kPsrC_UWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.10" name="cashboxif"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsrDPUWEe-xz_jKIkr2Qg" name="LightDisplay" implements="//@package/@subpackage.2/@classorinterface.8">
        <method id="_kPsrDfUWEe-xz_jKIkr2Qg" name="displayShopItem">
          <parameter id="_kPsrDvUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPsrD_UWEe-xz_jKIkr2Qg" name="price" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrEPUWEe-xz_jKIkr2Qg" name="displayTotal">
          <parameter id="_kPsrEfUWEe-xz_jKIkr2Qg" name="grossTotal" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrEvUWEe-xz_jKIkr2Qg" name="displayPaymentCard">
          <parameter id="_kPsrE_UWEe-xz_jKIkr2Qg" name="cardNumber" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrFPUWEe-xz_jKIkr2Qg" name="displayPaymentCash">
          <parameter id="_kPsrFfUWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
          <parameter id="_kPsrFvUWEe-xz_jKIkr2Qg" name="change" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsrF_UWEe-xz_jKIkr2Qg" name="Printer" implements="//@package/@subpackage.2/@classorinterface.12">
        <method id="_kPsrGPUWEe-xz_jKIkr2Qg" name="printShopItem">
          <parameter id="_kPsrGfUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPsrGvUWEe-xz_jKIkr2Qg" name="name" type="//@primitivetypes.8"/>
          <parameter id="_kPsrG_UWEe-xz_jKIkr2Qg" name="price" type="//@primitivetypes.3"/>
          <parameter id="_kPsrHPUWEe-xz_jKIkr2Qg" name="vat" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrHfUWEe-xz_jKIkr2Qg" name="printTotal">
          <parameter id="_kPsrHvUWEe-xz_jKIkr2Qg" name="netTotal" type="//@primitivetypes.3"/>
          <parameter id="_kPsrH_UWEe-xz_jKIkr2Qg" name="grossTotal" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrIPUWEe-xz_jKIkr2Qg" name="printPaymentCard">
          <parameter id="_kPsrIfUWEe-xz_jKIkr2Qg" name="cardNumber" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrIvUWEe-xz_jKIkr2Qg" name="printPaymentCash">
          <parameter id="_kPsrI_UWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
          <parameter id="_kPsrJPUWEe-xz_jKIkr2Qg" name="change" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsrJfUWEe-xz_jKIkr2Qg" name="Bank" implements="//@package/@subpackage.2/@classorinterface.6">
        <method id="_kPsrJvUWEe-xz_jKIkr2Qg" name="requestTransaction" returntype="//@package/@subpackage.1/@classorinterface.0">
          <parameter id="_kPsrJ_UWEe-xz_jKIkr2Qg" name="cardnumber" type="//@primitivetypes.3"/>
          <parameter id="_kPsrKPUWEe-xz_jKIkr2Qg" name="account" type="//@package/@subpackage.1/@classorinterface.1"/>
          <parameter id="_kPsrKfUWEe-xz_jKIkr2Qg" name="amount" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsrKvUWEe-xz_jKIkr2Qg" name="OtherStore" implements="//@package/@subpackage.2/@classorinterface.7">
        <method id="_kPsrK_UWEe-xz_jKIkr2Qg" name="orderProductsAvailableAtOtherStores" returntype="//@collectiontypes.1">
          <parameter id="_kPsrLPUWEe-xz_jKIkr2Qg" name="enterpriseTO" type="//@package/@subpackage.1/@classorinterface.3"/>
          <parameter id="_kPsrLfUWEe-xz_jKIkr2Qg" name="callingStore" type="//@package/@subpackage.1/@classorinterface.4"/>
          <parameter id="_kPsrLvUWEe-xz_jKIkr2Qg" name="productAmounts" type="//@collectiontypes.1"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPsrL_UWEe-xz_jKIkr2Qg" name="Billing" implements="//@package/@subpackage.2/@classorinterface.2 //@package/@subpackage.2/@classorinterface.18">
        <method id="_kPsrMPUWEe-xz_jKIkr2Qg" name="readCardNumber">
          <parameter id="_kPsrMfUWEe-xz_jKIkr2Qg" name="number" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrMvUWEe-xz_jKIkr2Qg" name="readPIN">
          <parameter id="_kPsrM_UWEe-xz_jKIkr2Qg" name="pin" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPsrNPUWEe-xz_jKIkr2Qg" name="registerSale">
          <parameter id="_kPsrNfUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPsrNvUWEe-xz_jKIkr2Qg" name="amount" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.6" name="banktransactionif"/>
        <field type="//@package/@subpackage.2/@classorinterface.9" name="cardreaderif"/>
      </classorinterface>
    </subpackage>
    <subpackage id="_kPpAq_UWEe-xz_jKIkr2Qg" name="datatypes">
      <classorinterface xsi:type="types:Class" id="_kPpAtvUWEe-xz_jKIkr2Qg" name="Acknowledgement">
        <field type="//@primitivetypes.7" name="success"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAt_UWEe-xz_jKIkr2Qg" name="Account">
        <field type="//@primitivetypes.3" name="number"/>
        <field type="//@primitivetypes.8" name="name"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAuPUWEe-xz_jKIkr2Qg" name="StoreWithEnterpriseTO">
        <field type="//@package/@subpackage.1/@classorinterface.4" name="store"/>
        <field type="//@package/@subpackage.1/@classorinterface.3" name="enterprise"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAufUWEe-xz_jKIkr2Qg" name="EnterpriseTO">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.8" name="name"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAuvUWEe-xz_jKIkr2Qg" name="StoreTO">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.8" name="name"/>
        <field type="//@primitivetypes.8" name="location"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAu_UWEe-xz_jKIkr2Qg" name="StockItemTO">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.3" name="salePrice"/>
        <field type="//@primitivetypes.3" name="amout"/>
        <field type="//@primitivetypes.3" name="minStock"/>
        <field type="//@primitivetypes.3" name="maxStock"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAvPUWEe-xz_jKIkr2Qg" name="ProductTO">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.3" name="barCode"/>
        <field type="//@primitivetypes.3" name="purchasePrice"/>
        <field type="//@primitivetypes.8" name="name"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAvfUWEe-xz_jKIkr2Qg" name="ComplexOrderTO">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@package/@subpackage.1/@classorinterface.10" name="deliveryDate"/>
        <field type="//@package/@subpackage.1/@classorinterface.10" name="orderingDate"/>
        <field type="//@collectiontypes.0" name="listOrders"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAvvUWEe-xz_jKIkr2Qg" name="OrderEntryTO">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.3" name="amount"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpAv_UWEe-xz_jKIkr2Qg" name="PersistenceContext"/>
      <classorinterface xsi:type="types:Class" id="_kPpnsPUWEe-xz_jKIkr2Qg" name="Date"/>
      <classorinterface xsi:type="types:Class" id="_kPpnsfUWEe-xz_jKIkr2Qg" name="ProductWithStockItemTO">
        <field type="//@package/@subpackage.1/@classorinterface.5" name="stockItem"/>
        <field type="//@package/@subpackage.1/@classorinterface.6" name="product"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnsvUWEe-xz_jKIkr2Qg" name="SupplierTO">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.8" name="name"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpns_UWEe-xz_jKIkr2Qg" name="ProductWithSupplierTO">
        <field type="//@package/@subpackage.1/@classorinterface.6" name="product"/>
        <field type="//@package/@subpackage.1/@classorinterface.12" name="supplier"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpntPUWEe-xz_jKIkr2Qg" name="ProductWithSupplierAndStockItemTO">
        <field type="//@package/@subpackage.1/@classorinterface.5" name="stockItem"/>
        <field type="//@package/@subpackage.1/@classorinterface.6" name="product"/>
        <field type="//@package/@subpackage.1/@classorinterface.12" name="supplier"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpntfUWEe-xz_jKIkr2Qg" name="ProductAmountTO">
        <field type="//@primitivetypes.3" name="amount"/>
        <field type="//@package/@subpackage.1/@classorinterface.6" name="product"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpntvUWEe-xz_jKIkr2Qg" name="ProductMovementTO">
        <field type="//@package/@subpackage.1/@classorinterface.4" name="originStore"/>
        <field type="//@package/@subpackage.1/@classorinterface.4" name="destinationStore"/>
        <field type="//@collectiontypes.1" name="productAmounts"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnt_UWEe-xz_jKIkr2Qg" name="SaleTO">
        <field type="//@package/@subpackage.1/@classorinterface.10" name="date"/>
        <field type="//@collectiontypes.2" name="products"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnuPUWEe-xz_jKIkr2Qg" name="Store">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.8" name="name"/>
        <field type="//@primitivetypes.8" name="location"/>
        <field type="//@primitivetypes.8" name="enterpriseName"/>
        <field type="//@package/@subpackage.1/@classorinterface.19" name="enterprise"/>
        <field type="//@collectiontypes.3" name="productOrders"/>
        <field type="//@collectiontypes.4" name="stockItems"/>
        <field type="//@package/@subpackage.1/@classorinterface.26" name="storeQuery"/>
        <field type="//@package/@subpackage.1/@classorinterface.20" name="enterpriseQuery"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnufUWEe-xz_jKIkr2Qg" name="TradingEnterprise">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.8" name="name"/>
        <field type="//@collectiontypes.5" name="suppliers"/>
        <field type="//@collectiontypes.6" name="stores"/>
        <field type="//@package/@subpackage.1/@classorinterface.20" name="enterpriseQuery"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnuvUWEe-xz_jKIkr2Qg" name="IEnterpriseQuery"/>
      <classorinterface xsi:type="types:Class" id="_kPpnu_UWEe-xz_jKIkr2Qg" name="ProductOrder">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@package/@subpackage.1/@classorinterface.10" name="deliveryDate"/>
        <field type="//@package/@subpackage.1/@classorinterface.10" name="orderingDate"/>
        <field type="//@collectiontypes.0" name="orderEntries"/>
        <field type="//@primitivetypes.8" name="storeName"/>
        <field type="//@primitivetypes.8" name="storeLocation"/>
        <field type="//@package/@subpackage.1/@classorinterface.18" name="store"/>
        <field type="//@package/@subpackage.1/@classorinterface.26" name="storeQuery"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnvPUWEe-xz_jKIkr2Qg" name="OrderEntry">
        <field type="//@primitivetypes.8" name="id"/>
        <field type="//@primitivetypes.3" name="amount"/>
        <field type="//@package/@subpackage.1/@classorinterface.23" name="productBarcode"/>
        <field type="//@package/@subpackage.1/@classorinterface.24" name="product"/>
        <field type="//@package/@subpackage.1/@classorinterface.26" name="storeQuery"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnvfUWEe-xz_jKIkr2Qg" name="Barcode">
        <field type="//@primitivetypes.3" name="barcode"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnvvUWEe-xz_jKIkr2Qg" name="Product">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@package/@subpackage.1/@classorinterface.23" name="barcode"/>
        <field type="//@primitivetypes.3" name="purchasePrice"/>
        <field type="//@primitivetypes.8" name="name"/>
        <field type="//@package/@subpackage.1/@classorinterface.25" name="supplier"/>
        <field type="//@package/@subpackage.1/@classorinterface.20" name="enterpriseQuery"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnv_UWEe-xz_jKIkr2Qg" name="ProductSupplier">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.8" name="name"/>
        <field type="//@collectiontypes.7" name="products"/>
        <field type="//@package/@subpackage.1/@classorinterface.20" name="enterpriseQuery"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_kPpnwPUWEe-xz_jKIkr2Qg" name="IStoreQuery"/>
      <classorinterface xsi:type="types:Class" id="_kPpnwfUWEe-xz_jKIkr2Qg" name="StockItem">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.7" name="salesPrice"/>
        <field type="//@primitivetypes.3" name="amount"/>
        <field type="//@primitivetypes.3" name="minStock"/>
        <field type="//@primitivetypes.3" name="maxStock"/>
        <field type="//@primitivetypes.3" name="incomingAmount"/>
        <field type="//@primitivetypes.8" name="storeName"/>
        <field type="//@primitivetypes.8" name="storeLocation"/>
        <field type="//@package/@subpackage.1/@classorinterface.23" name="productBarcode"/>
        <field type="//@package/@subpackage.1/@classorinterface.18" name="store"/>
        <field type="//@package/@subpackage.1/@classorinterface.24" name="product"/>
        <field type="//@package/@subpackage.1/@classorinterface.26" name="storeQuery"/>
      </classorinterface>
    </subpackage>
    <subpackage id="_kPpArPUWEe-xz_jKIkr2Qg" name="interfaces">
      <classorinterface xsi:type="types:Interface" id="_kPqOyPUWEe-xz_jKIkr2Qg" name="PrinterOutIf">
        <method id="_kPqOyfUWEe-xz_jKIkr2Qg" name="printShopItem" returntype="//@primitivetypes.8">
          <parameter id="_kPqOyvUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPqOy_UWEe-xz_jKIkr2Qg" name="name" type="//@primitivetypes.8"/>
          <parameter id="_kPqOzPUWEe-xz_jKIkr2Qg" name="price" type="//@primitivetypes.3"/>
          <parameter id="_kPqOzfUWEe-xz_jKIkr2Qg" name="vat" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPqOzvUWEe-xz_jKIkr2Qg" name="printTotal" returntype="//@primitivetypes.8">
          <parameter id="_kPqOz_UWEe-xz_jKIkr2Qg" name="netTotal" type="//@primitivetypes.3"/>
          <parameter id="_kPqO0PUWEe-xz_jKIkr2Qg" name="grossTotal" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPqO0fUWEe-xz_jKIkr2Qg" name="printPaymentCard" returntype="//@primitivetypes.8">
          <parameter id="_kPqO0vUWEe-xz_jKIkr2Qg" name="cardNumber" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPqO0_UWEe-xz_jKIkr2Qg" name="printPaymentCash" returntype="//@primitivetypes.8">
          <parameter id="_kPqO1PUWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
          <parameter id="_kPqO1fUWEe-xz_jKIkr2Qg" name="change" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPqO1vUWEe-xz_jKIkr2Qg" name="LightDisplayOutIf">
        <method id="_kPqO1_UWEe-xz_jKIkr2Qg" name="displayShopItem" returntype="//@primitivetypes.8">
          <parameter id="_kPq10PUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPq10fUWEe-xz_jKIkr2Qg" name="price" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPq10vUWEe-xz_jKIkr2Qg" name="displayTotal" returntype="//@primitivetypes.8">
          <parameter id="_kPq10_UWEe-xz_jKIkr2Qg" name="grossTotal" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPq11PUWEe-xz_jKIkr2Qg" name="displayPaymentCash" returntype="//@primitivetypes.8">
          <parameter id="_kPq11fUWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
          <parameter id="_kPq11vUWEe-xz_jKIkr2Qg" name="change" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq11_UWEe-xz_jKIkr2Qg" name="CardReaderOutIf">
        <method id="_kPq12PUWEe-xz_jKIkr2Qg" name="readCardNumber">
          <parameter id="_kPq12fUWEe-xz_jKIkr2Qg" name="number" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPq12vUWEe-xz_jKIkr2Qg" name="readPIN">
          <parameter id="_kPq12_UWEe-xz_jKIkr2Qg" name="pin" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq13PUWEe-xz_jKIkr2Qg" name="BarcodeScannerOutIf">
        <method id="_kPq13fUWEe-xz_jKIkr2Qg" name="readBarcode">
          <parameter id="_kPq13vUWEe-xz_jKIkr2Qg" name="barcode" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq13_UWEe-xz_jKIkr2Qg" name="CashBoxOutIf">
        <method id="_kPq14PUWEe-xz_jKIkr2Qg" name="startNewSale"/>
        <method id="_kPq14fUWEe-xz_jKIkr2Qg" name="completeItemRegistration"/>
        <method id="_kPq14vUWEe-xz_jKIkr2Qg" name="acknowladgeCashPayment" returntype="//@primitivetypes.0">
          <parameter id="_kPq14_UWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq15PUWEe-xz_jKIkr2Qg" name="StoreOutIf">
        <method id="_kPq15fUWEe-xz_jKIkr2Qg" name="getStore" returntype="//@package/@subpackage.1/@classorinterface.2"/>
        <method id="_kPq15vUWEe-xz_jKIkr2Qg" name="getProductsWithLowStock" returntype="//@collectiontypes.2"/>
        <method id="_kPq15_UWEe-xz_jKIkr2Qg" name="getAllProducts" returntype="//@collectiontypes.8"/>
        <method id="_kPq16fUWEe-xz_jKIkr2Qg" name="getAllProductsWithOptionalStockItem" returntype="//@collectiontypes.9"/>
        <method id="_kPq16_UWEe-xz_jKIkr2Qg" name="orderProducts" returntype="//@collectiontypes.10">
          <parameter id="_kPq17fUWEe-xz_jKIkr2Qg" name="complexOrder" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPq17vUWEe-xz_jKIkr2Qg" name="getOrder" returntype="//@package/@subpackage.1/@classorinterface.7">
          <parameter id="_kPq17_UWEe-xz_jKIkr2Qg" name="orderId" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPq18PUWEe-xz_jKIkr2Qg" name="rollInReceivedOrder">
          <parameter id="_kPq18fUWEe-xz_jKIkr2Qg" name="complexOrderTO" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPq18vUWEe-xz_jKIkr2Qg" name="changePrice" returntype="//@package/@subpackage.1/@classorinterface.11">
          <parameter id="_kPq18_UWEe-xz_jKIkr2Qg" name="stockItemTO" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_kPq19PUWEe-xz_jKIkr2Qg" name="markProductsUnavailableInStock">
          <parameter id="_kPq19fUWEe-xz_jKIkr2Qg" name="requiredProductsAndAmount" type="//@package/@subpackage.1/@classorinterface.16"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq19vUWEe-xz_jKIkr2Qg" name="BankTransactionIf">
        <method id="_kPq19_UWEe-xz_jKIkr2Qg" name="requestTransaction" returntype="//@package/@subpackage.1/@classorinterface.0">
          <parameter id="_kPq1-PUWEe-xz_jKIkr2Qg" name="cardnumber" type="//@primitivetypes.3"/>
          <parameter id="_kPq1-fUWEe-xz_jKIkr2Qg" name="account" type="//@package/@subpackage.1/@classorinterface.1"/>
          <parameter id="_kPq1-vUWEe-xz_jKIkr2Qg" name="amount" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq1-_UWEe-xz_jKIkr2Qg" name="ProductDispatcherIf">
        <method id="_kPq1_PUWEe-xz_jKIkr2Qg" name="orderProductsAvailableAtOtherStores" returntype="//@collectiontypes.1">
          <parameter id="_kPq1_fUWEe-xz_jKIkr2Qg" name="enterpriseTO" type="//@package/@subpackage.1/@classorinterface.3"/>
          <parameter id="_kPq1_vUWEe-xz_jKIkr2Qg" name="callingStore" type="//@package/@subpackage.1/@classorinterface.4"/>
          <parameter id="_kPq1__UWEe-xz_jKIkr2Qg" name="productAmounts" type="//@collectiontypes.1"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq2APUWEe-xz_jKIkr2Qg" name="LightDisplayIf">
        <method id="_kPq2AfUWEe-xz_jKIkr2Qg" name="displayShopItem">
          <parameter id="_kPq2AvUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPq2A_UWEe-xz_jKIkr2Qg" name="price" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPq2BPUWEe-xz_jKIkr2Qg" name="displayTotal">
          <parameter id="_kPq2BfUWEe-xz_jKIkr2Qg" name="grossTotal" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPq2BvUWEe-xz_jKIkr2Qg" name="displayPaymentCard">
          <parameter id="_kPq2B_UWEe-xz_jKIkr2Qg" name="cardNumber" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPq2CPUWEe-xz_jKIkr2Qg" name="displayPaymentCash">
          <parameter id="_kPq2CfUWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
          <parameter id="_kPq2CvUWEe-xz_jKIkr2Qg" name="change" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq2C_UWEe-xz_jKIkr2Qg" name="CardReaderIf">
        <method id="_kPq2DPUWEe-xz_jKIkr2Qg" name="readCardNumber">
          <parameter id="_kPq2DfUWEe-xz_jKIkr2Qg" name="number" type="//@primitivetypes.3"/>
          <parameter id="_kPq2DvUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq2D_UWEe-xz_jKIkr2Qg" name="CashBoxIf">
        <method id="_kPq2EPUWEe-xz_jKIkr2Qg" name="startNewSale"/>
        <method id="_kPq2EfUWEe-xz_jKIkr2Qg" name="completeItemRegistration"/>
        <method id="_kPq2EvUWEe-xz_jKIkr2Qg" name="acknowladgeCashPayment" returntype="//@primitivetypes.0">
          <parameter id="_kPq2E_UWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq2FPUWEe-xz_jKIkr2Qg" name="ScannerIf">
        <method id="_kPq2FfUWEe-xz_jKIkr2Qg" name="readBarcode">
          <parameter id="_kPq2FvUWEe-xz_jKIkr2Qg" name="barcode" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPq2F_UWEe-xz_jKIkr2Qg" name="PrinterIf">
        <method id="_kPq2GPUWEe-xz_jKIkr2Qg" name="printShopItem">
          <parameter id="_kPq2GfUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPq2GvUWEe-xz_jKIkr2Qg" name="name" type="//@primitivetypes.8"/>
          <parameter id="_kPq2G_UWEe-xz_jKIkr2Qg" name="price" type="//@primitivetypes.3"/>
          <parameter id="_kPq2HPUWEe-xz_jKIkr2Qg" name="vat" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPrc4PUWEe-xz_jKIkr2Qg" name="printTotal">
          <parameter id="_kPrc4fUWEe-xz_jKIkr2Qg" name="netTotal" type="//@primitivetypes.3"/>
          <parameter id="_kPrc4vUWEe-xz_jKIkr2Qg" name="grossTotal" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPrc4_UWEe-xz_jKIkr2Qg" name="printPaymentCard">
          <parameter id="_kPrc5PUWEe-xz_jKIkr2Qg" name="cardNumber" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPrc5fUWEe-xz_jKIkr2Qg" name="printPaymentCash">
          <parameter id="_kPrc5vUWEe-xz_jKIkr2Qg" name="amountPayed" type="//@primitivetypes.3"/>
          <parameter id="_kPrc5_UWEe-xz_jKIkr2Qg" name="change" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPrc6PUWEe-xz_jKIkr2Qg" name="StoreIf">
        <method id="_kPrc6fUWEe-xz_jKIkr2Qg" name="getStore" returntype="//@package/@subpackage.1/@classorinterface.2"/>
        <method id="_kPrc6vUWEe-xz_jKIkr2Qg" name="getProductsWithLowStock" returntype="//@collectiontypes.2"/>
        <method id="_kPrc6_UWEe-xz_jKIkr2Qg" name="getAllProducts" returntype="//@collectiontypes.8"/>
        <method id="_kPrc7PUWEe-xz_jKIkr2Qg" name="getAllProductsWithOptionalStockItem" returntype="//@collectiontypes.9"/>
        <method id="_kPrc7fUWEe-xz_jKIkr2Qg" name="orderProducts" returntype="//@collectiontypes.10">
          <parameter id="_kPrc7vUWEe-xz_jKIkr2Qg" name="complexOrder" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPrc7_UWEe-xz_jKIkr2Qg" name="getOrder" returntype="//@package/@subpackage.1/@classorinterface.7">
          <parameter id="_kPrc8PUWEe-xz_jKIkr2Qg" name="orderId" type="//@primitivetypes.3"/>
        </method>
        <method id="_kPrc8fUWEe-xz_jKIkr2Qg" name="rollInReceivedOrder">
          <parameter id="_kPrc8vUWEe-xz_jKIkr2Qg" name="complexOrderTO" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_kPrc8_UWEe-xz_jKIkr2Qg" name="changePrice" returntype="//@package/@subpackage.1/@classorinterface.11">
          <parameter id="_kPrc9PUWEe-xz_jKIkr2Qg" name="stockItemTO" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_kPrc9fUWEe-xz_jKIkr2Qg" name="markProductsUnavailableInStock">
          <parameter id="_kPrc9vUWEe-xz_jKIkr2Qg" name="requiredProductsAndAmount" type="//@package/@subpackage.1/@classorinterface.16"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPrc9_UWEe-xz_jKIkr2Qg" name="CashDeskConnectorIf">
        <method id="_kPrc-PUWEe-xz_jKIkr2Qg" name="bookSale">
          <parameter id="_kPrc-fUWEe-xz_jKIkr2Qg" name="sale" type="//@package/@subpackage.1/@classorinterface.17"/>
        </method>
        <method id="_kPrc-vUWEe-xz_jKIkr2Qg" name="getProductWithStockItem" returntype="//@package/@subpackage.1/@classorinterface.11">
          <parameter id="_kPrc-_UWEe-xz_jKIkr2Qg" name="productBarcode" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPrc_PUWEe-xz_jKIkr2Qg" name="PersistenceIf">
        <method id="_kPrc_fUWEe-xz_jKIkr2Qg" name="getPersistenceContext" returntype="//@package/@subpackage.1/@classorinterface.9"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPrc_vUWEe-xz_jKIkr2Qg" name="StoreQueryIf">
        <method id="_kPrc__UWEe-xz_jKIkr2Qg" name="queryStoreById" returntype="//@package/@subpackage.1/@classorinterface.18">
          <parameter id="_kPrdAPUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdAfUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdAvUWEe-xz_jKIkr2Qg" name="queryProducts" returntype="//@collectiontypes.7">
          <parameter id="_kPrdA_UWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdBPUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdBfUWEe-xz_jKIkr2Qg" name="queryLowStockItems" returntype="//@collectiontypes.4">
          <parameter id="_kPrdBvUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdB_UWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdCPUWEe-xz_jKIkr2Qg" name="queryLowStockItemsWithRespectToIncomingProducts" returntype="//@collectiontypes.4">
          <parameter id="_kPrdCfUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdCvUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdC_UWEe-xz_jKIkr2Qg" name="queryAllStockItems" returntype="//@collectiontypes.4">
          <parameter id="_kPrdDPUWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdDfUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdDvUWEe-xz_jKIkr2Qg" name="queryStockItem" returntype="//@package/@subpackage.1/@classorinterface.27">
          <parameter id="_kPrdD_UWEe-xz_jKIkr2Qg" name="stockId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdEPUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdEfUWEe-xz_jKIkr2Qg" name="queryStockItemById" returntype="//@package/@subpackage.1/@classorinterface.27">
          <parameter id="_kPrdEvUWEe-xz_jKIkr2Qg" name="stockId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdE_UWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdFPUWEe-xz_jKIkr2Qg" name="queryOrderById" returntype="//@package/@subpackage.1/@classorinterface.21">
          <parameter id="_kPrdFfUWEe-xz_jKIkr2Qg" name="orderId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdFvUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdF_UWEe-xz_jKIkr2Qg" name="queryProductById" returntype="//@package/@subpackage.1/@classorinterface.24">
          <parameter id="_kPrdGPUWEe-xz_jKIkr2Qg" name="productId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdGfUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
        <method id="_kPrdGvUWEe-xz_jKIkr2Qg" name="getStockItems" returntype="//@collectiontypes.4">
          <parameter id="_kPrdG_UWEe-xz_jKIkr2Qg" name="storeId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdHPUWEe-xz_jKIkr2Qg" name="productId" type="//@primitivetypes.3"/>
          <parameter id="_kPrdHfUWEe-xz_jKIkr2Qg" name="pctx" type="//@package/@subpackage.1/@classorinterface.9"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_kPrdHvUWEe-xz_jKIkr2Qg" name="SaleRegisteredEvent"/>
      <classorinterface xsi:type="types:Interface" id="_kPrdH_UWEe-xz_jKIkr2Qg" name="BillingInteraction">
        <method id="_kPrdIPUWEe-xz_jKIkr2Qg" name="registerSale">
          <parameter id="_kPrdIfUWEe-xz_jKIkr2Qg" name="id" type="//@primitivetypes.3"/>
          <parameter id="_kPrdIvUWEe-xz_jKIkr2Qg" name="amount" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
    </subpackage>
  </package>
</java:JavaRoot>
