<?xml version="1.0" encoding="ASCII"?>
<java:JavaRoot xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:java="http://www.example.org/java" xmlns:types="http://www.example.org/java/types">
  <primitivetypes id="_hkNVIHiTEe-RYawRRhYWUA" name="boolean"/>
  <primitivetypes id="_hkNVIXiTEe-RYawRRhYWUA" name="int" kind="BYTE"/>
  <primitivetypes id="_hkNVIniTEe-RYawRRhYWUA" name="short" kind="SHORT"/>
  <primitivetypes id="_hkNVI3iTEe-RYawRRhYWUA" name="int" kind="INT"/>
  <primitivetypes id="_hkNVJHiTEe-RYawRRhYWUA" name="long" kind="LONG"/>
  <primitivetypes id="_hkNVJXiTEe-RYawRRhYWUA" name="char" kind="CHAR"/>
  <primitivetypes id="_hkNVJniTEe-RYawRRhYWUA" name="float" kind="FLOAT"/>
  <primitivetypes id="_hkNVJ3iTEe-RYawRRhYWUA" name="double" kind="DOUBLE"/>
  <primitivetypes id="_hkNVKHiTEe-RYawRRhYWUA" name="String" kind="STRING"/>
  <package id="_hkWfG3iTEe-RYawRRhYWUA" name="edu.kit.kastel.sdq.coupling.casestudy.jpmail">
    <subpackage id="_hkKR0HiTEe-RYawRRhYWUA" name="components">
      <classorinterface xsi:type="types:Class" id="_hkS0zniTEe-RYawRRhYWUA" name="MailSendingFacade" implements="//@package/@subpackage.2/@classorinterface.0">
        <method id="_hkUp4HiTEe-RYawRRhYWUA" name="sendMail">
          <parameter id="_hkVQ8HiTEe-RYawRRhYWUA" name="header" type="//@package/@subpackage.1/@classorinterface.2"/>
          <parameter id="_hkVQ8XiTEe-RYawRRhYWUA" name="body" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.4" name="emaildispatchment"/>
        <field type="//@package/@subpackage.2/@classorinterface.5" name="policystorageaccess"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkV4AHiTEe-RYawRRhYWUA" name="MailReceivingFacade" implements="//@package/@subpackage.2/@classorinterface.1 //@package/@subpackage.2/@classorinterface.2 //@package/@subpackage.2/@classorinterface.4">
        <method id="_hkV4AXiTEe-RYawRRhYWUA" name="receiveMail" returntype="//@package/@subpackage.1/@classorinterface.0"/>
        <method id="_hkV4AniTEe-RYawRRhYWUA" name="addPrivateKey">
          <parameter id="_hkV4A3iTEe-RYawRRhYWUA" name="privateKey" type="//@package/@subpackage.1/@classorinterface.4"/>
        </method>
        <method id="_hkV4BHiTEe-RYawRRhYWUA" name="dispatchMail">
          <parameter id="_hkV4BXiTEe-RYawRRhYWUA" name="header" type="//@package/@subpackage.1/@classorinterface.2"/>
          <parameter id="_hkV4BniTEe-RYawRRhYWUA" name="body" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.8" name="emailstoring"/>
        <field type="//@package/@subpackage.2/@classorinterface.9" name="privatekeystore"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkV4B3iTEe-RYawRRhYWUA" name="PolicyManagementFacade" implements="//@package/@subpackage.2/@classorinterface.3 //@package/@subpackage.2/@classorinterface.5">
        <method id="_hkV4CHiTEe-RYawRRhYWUA" name="addPublicKey">
          <parameter id="_hkV4CXiTEe-RYawRRhYWUA" name="publicKey" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
        <method id="_hkV4CniTEe-RYawRRhYWUA" name="addPolicy">
          <parameter id="_hkV4C3iTEe-RYawRRhYWUA" name="policy" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_hkV4DHiTEe-RYawRRhYWUA" name="getPublicKey" returntype="//@package/@subpackage.1/@classorinterface.3"/>
        <method id="_hkV4DXiTEe-RYawRRhYWUA" name="getPolicy" returntype="//@package/@subpackage.1/@classorinterface.5"/>
        <field type="//@package/@subpackage.2/@classorinterface.6" name="policystoring"/>
        <field type="//@package/@subpackage.2/@classorinterface.7" name="publickeystore"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkV4DniTEe-RYawRRhYWUA" name="SMTP" implements="//@package/@subpackage.2/@classorinterface.4">
        <method id="_hkV4D3iTEe-RYawRRhYWUA" name="dispatchMail">
          <parameter id="_hkV4EHiTEe-RYawRRhYWUA" name="header" type="//@package/@subpackage.1/@classorinterface.2"/>
          <parameter id="_hkV4EXiTEe-RYawRRhYWUA" name="body" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.4" name="emaildispatchment"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkV4EniTEe-RYawRRhYWUA" name="POP3" implements="//@package/@subpackage.2/@classorinterface.4">
        <method id="_hkV4E3iTEe-RYawRRhYWUA" name="dispatchMail">
          <parameter id="_hkV4FHiTEe-RYawRRhYWUA" name="header" type="//@package/@subpackage.1/@classorinterface.2"/>
          <parameter id="_hkV4FXiTEe-RYawRRhYWUA" name="body" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.4" name="emaildispatchment"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkV4FniTEe-RYawRRhYWUA" name="PolicyStore" implements="//@package/@subpackage.2/@classorinterface.6">
        <method id="_hkV4F3iTEe-RYawRRhYWUA" name="addPolicy">
          <parameter id="_hkV4GHiTEe-RYawRRhYWUA" name="policy" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_hkV4GXiTEe-RYawRRhYWUA" name="getPolicy" returntype="//@package/@subpackage.1/@classorinterface.5"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkV4GniTEe-RYawRRhYWUA" name="PublicKeyStorage" implements="//@package/@subpackage.2/@classorinterface.7">
        <method id="_hkWfEHiTEe-RYawRRhYWUA" name="addPublicKey">
          <parameter id="_hkWfEXiTEe-RYawRRhYWUA" name="publicKey" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
        <method id="_hkWfEniTEe-RYawRRhYWUA" name="getPublicKey" returntype="//@package/@subpackage.1/@classorinterface.3"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkWfE3iTEe-RYawRRhYWUA" name="EMailStorage" implements="//@package/@subpackage.2/@classorinterface.8">
        <method id="_hkWfFHiTEe-RYawRRhYWUA" name="getEMail" returntype="//@package/@subpackage.1/@classorinterface.0"/>
        <method id="_hkWfFXiTEe-RYawRRhYWUA" name="addEMail">
          <parameter id="_hkWfFniTEe-RYawRRhYWUA" name="email" type="//@package/@subpackage.1/@classorinterface.0"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkWfF3iTEe-RYawRRhYWUA" name="PrivateKeyStorage" implements="//@package/@subpackage.2/@classorinterface.9">
        <method id="_hkWfGHiTEe-RYawRRhYWUA" name="getPrivateKey" returntype="//@package/@subpackage.1/@classorinterface.4"/>
        <method id="_hkWfGXiTEe-RYawRRhYWUA" name="addPrivateKey">
          <parameter id="_hkWfGniTEe-RYawRRhYWUA" name="privateKey" type="//@package/@subpackage.1/@classorinterface.4"/>
        </method>
      </classorinterface>
    </subpackage>
    <subpackage id="_hkKR0XiTEe-RYawRRhYWUA" name="datatypes">
      <classorinterface xsi:type="types:Class" id="_hkOjQHiTEe-RYawRRhYWUA" name="EMail">
        <field type="//@package/@subpackage.1/@classorinterface.2" name="header"/>
        <field type="//@package/@subpackage.1/@classorinterface.1" name="body"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_hkPxYHiTEe-RYawRRhYWUA" name="EMailBody"/>
      <classorinterface xsi:type="types:Class" id="_hkPxYXiTEe-RYawRRhYWUA" name="EMailHeader"/>
      <classorinterface xsi:type="types:Class" id="_hkPxYniTEe-RYawRRhYWUA" name="PublicKey"/>
      <classorinterface xsi:type="types:Class" id="_hkPxY3iTEe-RYawRRhYWUA" name="PrivateKey"/>
      <classorinterface xsi:type="types:Class" id="_hkPxZHiTEe-RYawRRhYWUA" name="Policy"/>
    </subpackage>
    <subpackage id="_hkKR0niTEe-RYawRRhYWUA" name="interfaces">
      <classorinterface xsi:type="types:Interface" id="_hkRmkHiTEe-RYawRRhYWUA" name="MailSending">
        <method id="_hkSNoHiTEe-RYawRRhYWUA" name="sendMail">
          <parameter id="_hkSNoXiTEe-RYawRRhYWUA" name="header" type="//@package/@subpackage.1/@classorinterface.2"/>
          <parameter id="_hkSNoniTEe-RYawRRhYWUA" name="body" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkSNo3iTEe-RYawRRhYWUA" name="MailReceiving">
        <method id="_hkSNpHiTEe-RYawRRhYWUA" name="receiveMail" returntype="//@package/@subpackage.1/@classorinterface.0"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkSNpXiTEe-RYawRRhYWUA" name="PrivateKeyStorage">
        <method id="_hkS0sHiTEe-RYawRRhYWUA" name="addPrivateKey">
          <parameter id="_hkS0sXiTEe-RYawRRhYWUA" name="privateKey" type="//@package/@subpackage.1/@classorinterface.4"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkS0sniTEe-RYawRRhYWUA" name="PolicyStorage">
        <method id="_hkS0s3iTEe-RYawRRhYWUA" name="addPublicKey">
          <parameter id="_hkS0tHiTEe-RYawRRhYWUA" name="publicKey" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
        <method id="_hkS0tXiTEe-RYawRRhYWUA" name="addPolicy">
          <parameter id="_hkS0tniTEe-RYawRRhYWUA" name="policy" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkS0t3iTEe-RYawRRhYWUA" name="EMailDispatchment">
        <method id="_hkS0uHiTEe-RYawRRhYWUA" name="dispatchMail">
          <parameter id="_hkS0uXiTEe-RYawRRhYWUA" name="header" type="//@package/@subpackage.1/@classorinterface.2"/>
          <parameter id="_hkS0uniTEe-RYawRRhYWUA" name="body" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkS0u3iTEe-RYawRRhYWUA" name="PolicyStorageAccess">
        <method id="_hkS0vHiTEe-RYawRRhYWUA" name="getPublicKey" returntype="//@package/@subpackage.1/@classorinterface.3"/>
        <method id="_hkS0vXiTEe-RYawRRhYWUA" name="getPolicy" returntype="//@package/@subpackage.1/@classorinterface.5"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkS0vniTEe-RYawRRhYWUA" name="PolicyStoring">
        <method id="_hkS0v3iTEe-RYawRRhYWUA" name="addPolicy">
          <parameter id="_hkS0wHiTEe-RYawRRhYWUA" name="policy" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_hkS0wXiTEe-RYawRRhYWUA" name="getPolicy" returntype="//@package/@subpackage.1/@classorinterface.5"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkS0wniTEe-RYawRRhYWUA" name="PublicKeyStore">
        <method id="_hkS0w3iTEe-RYawRRhYWUA" name="addPublicKey">
          <parameter id="_hkS0xHiTEe-RYawRRhYWUA" name="publicKey" type="//@package/@subpackage.1/@classorinterface.3"/>
        </method>
        <method id="_hkS0xXiTEe-RYawRRhYWUA" name="getPublicKey" returntype="//@package/@subpackage.1/@classorinterface.3"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkS0xniTEe-RYawRRhYWUA" name="EMailStoring">
        <method id="_hkS0x3iTEe-RYawRRhYWUA" name="getEMail" returntype="//@package/@subpackage.1/@classorinterface.0"/>
        <method id="_hkS0yHiTEe-RYawRRhYWUA" name="addEMail">
          <parameter id="_hkS0yXiTEe-RYawRRhYWUA" name="email" type="//@package/@subpackage.1/@classorinterface.0"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_hkS0yniTEe-RYawRRhYWUA" name="PrivateKeyStore">
        <method id="_hkS0y3iTEe-RYawRRhYWUA" name="getPrivateKey" returntype="//@package/@subpackage.1/@classorinterface.4"/>
        <method id="_hkS0zHiTEe-RYawRRhYWUA" name="addPrivateKey">
          <parameter id="_hkS0zXiTEe-RYawRRhYWUA" name="privateKey" type="//@package/@subpackage.1/@classorinterface.4"/>
        </method>
      </classorinterface>
    </subpackage>
  </package>
</java:JavaRoot>
