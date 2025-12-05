<?xml version="1.0" encoding="ASCII"?>
<java:JavaRoot xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:java="http://www.example.org/java" xmlns:types="http://www.example.org/java/types">
  <primitivetypes id="_AsCMA3pFEe-t65qwUtSHiQ" name="boolean"/>
  <primitivetypes id="_AsCMBHpFEe-t65qwUtSHiQ" name="int" kind="BYTE"/>
  <primitivetypes id="_AsCMBXpFEe-t65qwUtSHiQ" name="short" kind="SHORT"/>
  <primitivetypes id="_AsCMBnpFEe-t65qwUtSHiQ" name="int" kind="INT"/>
  <primitivetypes id="_AsCMB3pFEe-t65qwUtSHiQ" name="long" kind="LONG"/>
  <primitivetypes id="_AsCMCHpFEe-t65qwUtSHiQ" name="char" kind="CHAR"/>
  <primitivetypes id="_AsCMCXpFEe-t65qwUtSHiQ" name="float" kind="FLOAT"/>
  <primitivetypes id="_AsCMCnpFEe-t65qwUtSHiQ" name="double" kind="DOUBLE"/>
  <primitivetypes id="_AsCMC3pFEe-t65qwUtSHiQ" name="String" kind="STRING"/>
  <collectiontypes id="_AsEBMHpFEe-t65qwUtSHiQ" name="Collection&lt;PassengerDetails>" type="//@package/@subpackage.1/@classorinterface.3"/>
  <collectiontypes id="_AsEoQ3pFEe-t65qwUtSHiQ" name="Collection&lt;FlightOffer>" type="//@package/@subpackage.1/@classorinterface.2"/>
  <collectiontypes id="_AsEoYHpFEe-t65qwUtSHiQ" name="Collection&lt;Flight>" type="//@package/@subpackage.1/@classorinterface.5"/>
  <package id="_AsF2fXpFEe-t65qwUtSHiQ" name="edu.kit.kastel.sdq.coupling.casestudy.travelplanner">
    <subpackage id="_AsCMAHpFEe-t65qwUtSHiQ" name="components">
      <classorinterface xsi:type="types:Class" id="_AsEoZ3pFEe-t65qwUtSHiQ" name="Airline" implements="//@package/@subpackage.2/@classorinterface.3 //@package/@subpackage.2/@classorinterface.8">
        <method id="_AsFPUHpFEe-t65qwUtSHiQ" name="bookFlightOffer" returntype="//@primitivetypes.0">
          <parameter id="_AsFPUXpFEe-t65qwUtSHiQ" name="offerId" type="//@primitivetypes.3"/>
          <parameter id="_AsFPUnpFEe-t65qwUtSHiQ" name="ccd_decl" type="//@package/@subpackage.1/@classorinterface.1"/>
          <parameter id="_AsFPU3pFEe-t65qwUtSHiQ" name="passengerDetails" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
        <method id="_AsFPVHpFEe-t65qwUtSHiQ" name="getFlightOffers" returntype="//@collectiontypes.1">
          <parameter id="_AsFPVXpFEe-t65qwUtSHiQ" name="requestData" type="//@package/@subpackage.1/@classorinterface.0"/>
          <parameter id="_AsFPVnpFEe-t65qwUtSHiQ" name="discount" type="//@package/@subpackage.1/@classorinterface.4"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.0" name="commission"/>
        <field type="//@package/@subpackage.2/@classorinterface.6" name="airlinelogging"/>
        <field type="//@package/@subpackage.2/@classorinterface.9" name="flightstoring"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsFPV3pFEe-t65qwUtSHiQ" name="TravelPlanner" implements="//@package/@subpackage.2/@classorinterface.1 //@package/@subpackage.2/@classorinterface.2">
        <method id="_AsFPWHpFEe-t65qwUtSHiQ" name="getFlightOffers" returntype="//@collectiontypes.1">
          <parameter id="_AsFPWXpFEe-t65qwUtSHiQ" name="requestData" type="//@package/@subpackage.1/@classorinterface.0"/>
        </method>
        <method id="_AsFPWnpFEe-t65qwUtSHiQ" name="bookSelected" returntype="//@primitivetypes.0">
          <parameter id="_AsFPW3pFEe-t65qwUtSHiQ" name="flightOffer" type="//@package/@subpackage.1/@classorinterface.2"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.1" name="flightquery"/>
        <field type="//@package/@subpackage.2/@classorinterface.4" name="declassification"/>
        <field type="//@package/@subpackage.2/@classorinterface.3" name="airlinebooking"/>
        <field type="//@package/@subpackage.1/@classorinterface.3" name="passengerdetails"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsF2YHpFEe-t65qwUtSHiQ" name="CreditCardCenter" implements="//@package/@subpackage.2/@classorinterface.4">
        <method id="_AsF2YXpFEe-t65qwUtSHiQ" name="releaseCCD" returntype="//@package/@subpackage.1/@classorinterface.1">
          <parameter id="_AsF2YnpFEe-t65qwUtSHiQ" name="airlineId" type="//@primitivetypes.3"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.5" name="declassificationconfirmation"/>
        <field type="//@package/@subpackage.1/@classorinterface.1" name="ccd"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsF2Y3pFEe-t65qwUtSHiQ" name="UserInterface" implements="//@package/@subpackage.2/@classorinterface.1 //@package/@subpackage.2/@classorinterface.5 //@package/@subpackage.2/@classorinterface.2">
        <method id="_AsF2ZHpFEe-t65qwUtSHiQ" name="getFlightOffers" returntype="//@collectiontypes.1">
          <parameter id="_AsF2ZXpFEe-t65qwUtSHiQ" name="requestData" type="//@package/@subpackage.1/@classorinterface.0"/>
        </method>
        <method id="_AsF2ZnpFEe-t65qwUtSHiQ" name="confirmRelease" returntype="//@primitivetypes.0">
          <parameter id="_AsF2Z3pFEe-t65qwUtSHiQ" name="ccd" type="//@package/@subpackage.1/@classorinterface.1"/>
          <parameter id="_AsF2aHpFEe-t65qwUtSHiQ" name="airlineId" type="//@primitivetypes.3"/>
        </method>
        <method id="_AsF2aXpFEe-t65qwUtSHiQ" name="bookSelected" returntype="//@primitivetypes.0">
          <parameter id="_AsF2anpFEe-t65qwUtSHiQ" name="flightOffer" type="//@package/@subpackage.1/@classorinterface.2"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.1" name="flightquery"/>
        <field type="//@package/@subpackage.2/@classorinterface.4" name="declassification"/>
        <field type="//@package/@subpackage.2/@classorinterface.2" name="flightbooking"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsF2a3pFEe-t65qwUtSHiQ" name="AirlineLogger" implements="//@package/@subpackage.2/@classorinterface.6">
        <method id="_AsF2bHpFEe-t65qwUtSHiQ" name="log">
          <parameter id="_AsF2bXpFEe-t65qwUtSHiQ" name="entry" type="//@package/@subpackage.1/@classorinterface.6"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.7" name="logstoring"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsF2bnpFEe-t65qwUtSHiQ" name="TravelAgency" implements="//@package/@subpackage.2/@classorinterface.1 //@package/@subpackage.2/@classorinterface.0">
        <method id="_AsF2b3pFEe-t65qwUtSHiQ" name="getFlightOffers" returntype="//@collectiontypes.1">
          <parameter id="_AsF2cHpFEe-t65qwUtSHiQ" name="requestData" type="//@package/@subpackage.1/@classorinterface.0"/>
        </method>
        <method id="_AsF2cXpFEe-t65qwUtSHiQ" name="payCommission" returntype="//@primitivetypes.0"/>
        <field type="//@package/@subpackage.2/@classorinterface.8" name="flightquerywithdiscount"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsF2cnpFEe-t65qwUtSHiQ" name="FlightStorage" implements="//@package/@subpackage.2/@classorinterface.9">
        <method id="_AsF2c3pFEe-t65qwUtSHiQ" name="retrieveFlights" returntype="//@collectiontypes.2">
          <parameter id="_AsF2dHpFEe-t65qwUtSHiQ" name="airlineId" type="//@primitivetypes.3"/>
        </method>
        <method id="_AsF2dXpFEe-t65qwUtSHiQ" name="addToFlight">
          <parameter id="_AsF2dnpFEe-t65qwUtSHiQ" name="flightId" type="//@primitivetypes.3"/>
          <parameter id="_AsF2d3pFEe-t65qwUtSHiQ" name="details" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
        <method id="_AsF2eHpFEe-t65qwUtSHiQ" name="addFlight">
          <parameter id="_AsF2eXpFEe-t65qwUtSHiQ" name="flight" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsF2enpFEe-t65qwUtSHiQ" name="LoggingDB" implements="//@package/@subpackage.2/@classorinterface.7">
        <method id="_AsF2e3pFEe-t65qwUtSHiQ" name="storeLog">
          <parameter id="_AsF2fHpFEe-t65qwUtSHiQ" name="entry" type="//@package/@subpackage.1/@classorinterface.6"/>
        </method>
      </classorinterface>
    </subpackage>
    <subpackage id="_AsCMAXpFEe-t65qwUtSHiQ" name="datatypes">
      <classorinterface xsi:type="types:Class" id="_AsCzEHpFEe-t65qwUtSHiQ" name="RequestData">
        <field type="//@primitivetypes.8" name="destination"/>
        <field type="//@primitivetypes.8" name="date"/>
        <field type="//@primitivetypes.8" name="userName"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsDaIHpFEe-t65qwUtSHiQ" name="CreditCardDetails">
        <field type="//@primitivetypes.3" name="creditCardNumber"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsDaIXpFEe-t65qwUtSHiQ" name="FlightOffer">
        <field type="//@primitivetypes.3" name="id"/>
        <field type="//@primitivetypes.3" name="airlineId"/>
        <field type="//@primitivetypes.3" name="cost"/>
        <field type="//@primitivetypes.3" name="flightId"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsDaInpFEe-t65qwUtSHiQ" name="PassengerDetails">
        <field type="//@primitivetypes.8" name="firstName"/>
        <field type="//@primitivetypes.8" name="lastName"/>
        <field type="//@primitivetypes.3" name="age"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsDaI3pFEe-t65qwUtSHiQ" name="DiscountDetails">
        <field type="//@primitivetypes.8" name="discountId"/>
        <field type="//@primitivetypes.8" name="travelAgencyName"/>
        <field type="//@primitivetypes.3" name="discount"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsDaJHpFEe-t65qwUtSHiQ" name="Flight">
        <field type="//@primitivetypes.8" name="from"/>
        <field type="//@primitivetypes.8" name="to"/>
        <field type="//@primitivetypes.8" name="date"/>
        <field type="//@primitivetypes.3" name="airlineId"/>
        <field type="//@collectiontypes.0" name="passengers"/>
        <field type="//@primitivetypes.3" name="flightId"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_AsDaJXpFEe-t65qwUtSHiQ" name="Object"/>
    </subpackage>
    <subpackage id="_AsCMAnpFEe-t65qwUtSHiQ" name="interfaces">
      <classorinterface xsi:type="types:Interface" id="_AsEBMXpFEe-t65qwUtSHiQ" name="Commission">
        <method id="_AsEoQHpFEe-t65qwUtSHiQ" name="payCommission" returntype="//@primitivetypes.0"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoQXpFEe-t65qwUtSHiQ" name="FlightQuery">
        <method id="_AsEoQnpFEe-t65qwUtSHiQ" name="getFlightOffers" returntype="//@collectiontypes.1">
          <parameter id="_AsEoRHpFEe-t65qwUtSHiQ" name="requestData" type="//@package/@subpackage.1/@classorinterface.0"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoRXpFEe-t65qwUtSHiQ" name="FlightBooking">
        <method id="_AsEoRnpFEe-t65qwUtSHiQ" name="bookSelected" returntype="//@primitivetypes.0">
          <parameter id="_AsEoR3pFEe-t65qwUtSHiQ" name="flightOffer" type="//@package/@subpackage.1/@classorinterface.2"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoSHpFEe-t65qwUtSHiQ" name="AirlineBooking">
        <method id="_AsEoSXpFEe-t65qwUtSHiQ" name="bookFlightOffer" returntype="//@primitivetypes.0">
          <parameter id="_AsEoSnpFEe-t65qwUtSHiQ" name="offerId" type="//@primitivetypes.3"/>
          <parameter id="_AsEoS3pFEe-t65qwUtSHiQ" name="ccd_decl" type="//@package/@subpackage.1/@classorinterface.1"/>
          <parameter id="_AsEoTHpFEe-t65qwUtSHiQ" name="passengerDetails" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoTXpFEe-t65qwUtSHiQ" name="Declassification">
        <method id="_AsEoTnpFEe-t65qwUtSHiQ" name="releaseCCD" returntype="//@package/@subpackage.1/@classorinterface.1">
          <parameter id="_AsEoT3pFEe-t65qwUtSHiQ" name="airlineId" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoUHpFEe-t65qwUtSHiQ" name="DeclassificationConfirmation">
        <method id="_AsEoUXpFEe-t65qwUtSHiQ" name="confirmRelease" returntype="//@primitivetypes.0">
          <parameter id="_AsEoUnpFEe-t65qwUtSHiQ" name="ccd" type="//@package/@subpackage.1/@classorinterface.1"/>
          <parameter id="_AsEoU3pFEe-t65qwUtSHiQ" name="airlineId" type="//@primitivetypes.3"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoVHpFEe-t65qwUtSHiQ" name="AirlineLogging">
        <method id="_AsEoVXpFEe-t65qwUtSHiQ" name="log">
          <parameter id="_AsEoVnpFEe-t65qwUtSHiQ" name="entry" type="//@package/@subpackage.1/@classorinterface.6"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoV3pFEe-t65qwUtSHiQ" name="LogStoring">
        <method id="_AsEoWHpFEe-t65qwUtSHiQ" name="storeLog">
          <parameter id="_AsEoWXpFEe-t65qwUtSHiQ" name="entry" type="//@package/@subpackage.1/@classorinterface.6"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoWnpFEe-t65qwUtSHiQ" name="FlightQueryWithDiscount">
        <method id="_AsEoW3pFEe-t65qwUtSHiQ" name="getFlightOffers" returntype="//@collectiontypes.1">
          <parameter id="_AsEoXHpFEe-t65qwUtSHiQ" name="requestData" type="//@package/@subpackage.1/@classorinterface.0"/>
          <parameter id="_AsEoXXpFEe-t65qwUtSHiQ" name="discount" type="//@package/@subpackage.1/@classorinterface.4"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_AsEoXnpFEe-t65qwUtSHiQ" name="FlightStoring">
        <method id="_AsEoX3pFEe-t65qwUtSHiQ" name="retrieveFlights" returntype="//@collectiontypes.2">
          <parameter id="_AsEoYXpFEe-t65qwUtSHiQ" name="airlineId" type="//@primitivetypes.3"/>
        </method>
        <method id="_AsEoYnpFEe-t65qwUtSHiQ" name="addToFlight">
          <parameter id="_AsEoY3pFEe-t65qwUtSHiQ" name="flightId" type="//@primitivetypes.3"/>
          <parameter id="_AsEoZHpFEe-t65qwUtSHiQ" name="details" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
        <method id="_AsEoZXpFEe-t65qwUtSHiQ" name="addFlight">
          <parameter id="_AsEoZnpFEe-t65qwUtSHiQ" name="flight" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
      </classorinterface>
    </subpackage>
  </package>
</java:JavaRoot>
