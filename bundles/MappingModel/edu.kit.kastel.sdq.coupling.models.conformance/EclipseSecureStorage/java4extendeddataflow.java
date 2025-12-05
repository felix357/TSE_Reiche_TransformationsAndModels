<?xml version="1.0" encoding="ASCII"?>
<java:JavaRoot xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:java="http://www.example.org/java" xmlns:types="http://www.example.org/java/types">
  <primitivetypes id="_mg5foB05EfCn6vGa4mnYTA" name="boolean"/>
  <primitivetypes id="_mg6GsB05EfCn6vGa4mnYTA" name="byte" kind="BYTE"/>
  <primitivetypes id="_mg6GsR05EfCn6vGa4mnYTA" name="short" kind="SHORT"/>
  <primitivetypes id="_mg6Gsh05EfCn6vGa4mnYTA" name="int" kind="INT"/>
  <primitivetypes id="_mg6Gsx05EfCn6vGa4mnYTA" name="long" kind="LONG"/>
  <primitivetypes id="_mg6GtB05EfCn6vGa4mnYTA" name="char" kind="CHAR"/>
  <primitivetypes id="_mg6GtR05EfCn6vGa4mnYTA" name="float" kind="FLOAT"/>
  <primitivetypes id="_mg6Gth05EfCn6vGa4mnYTA" name="double" kind="DOUBLE"/>
  <primitivetypes id="_mg6Gtx05EfCn6vGa4mnYTA" name="String" kind="STRING"/>
  <collectiontypes id="_mhBbcB05EfCn6vGa4mnYTA" name="String[][]" type="//@primitivetypes.8"/>
  <collectiontypes id="_mhCplR05EfCn6vGa4mnYTA" name="Principal[]" type="//@package/@subpackage.1/@classorinterface.2"/>
  <collectiontypes id="_mhDQ0B05EfCn6vGa4mnYTA" name="byte[]" type="//@primitivetypes.1"/>
  <package id="_mhJ-gh05EfCn6vGa4mnYTA" name="edu.kit.kastel.sdq.coupling.casestudy.eclipsesecurestorage">
    <subpackage id="_mg2cUB05EfCn6vGa4mnYTA" name="components">
      <classorinterface xsi:type="types:Class" id="_mhEewB05EfCn6vGa4mnYTA" name="PasswordManagement" implements="//@package/@subpackage.2/@classorinterface.0">
        <method id="_mhGT8B05EfCn6vGa4mnYTA" name="setupRecovery">
          <parameter id="_mhHiEB05EfCn6vGa4mnYTA" name="challengeResponse" type="//@collectiontypes.0"/>
          <parameter id="_mhIJIB05EfCn6vGa4mnYTA" name="moduleID" type="//@primitivetypes.8"/>
          <parameter id="_mhIJIR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <field type="//@package/@subpackage.2/@classorinterface.8" name="isecurepreferencesmain"/>
      </classorinterface>
      <classorinterface xsi:type="types:Class" id="_mhJXQB05EfCn6vGa4mnYTA" name="SecurePreferences" implements="//@package/@subpackage.2/@classorinterface.8">
        <method id="_mhJXQR05EfCn6vGa4mnYTA" name="put">
          <parameter id="_mhJXQh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJXQx05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.8"/>
          <parameter id="_mhJXRB05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhJXRR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJXRh05EfCn6vGa4mnYTA" name="get">
          <parameter id="_mhJXRx05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJXSB05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.8"/>
          <parameter id="_mhJXSR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJXSh05EfCn6vGa4mnYTA" name="remove">
          <parameter id="_mhJXSx05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhJXTB05EfCn6vGa4mnYTA" name="clear"/>
        <method id="_mhJXTR05EfCn6vGa4mnYTA" name="keys" returntype="//@collectiontypes.0"/>
        <method id="_mhJXTh05EfCn6vGa4mnYTA" name="childrenNames" returntype="//@collectiontypes.0"/>
        <method id="_mhJXTx05EfCn6vGa4mnYTA" name="parent" returntype="//@package/@subpackage.1/@classorinterface.9"/>
        <method id="_mhJXUB05EfCn6vGa4mnYTA" name="node" returntype="//@package/@subpackage.1/@classorinterface.9">
          <parameter id="_mhJXUR05EfCn6vGa4mnYTA" name="pathName" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhJXUh05EfCn6vGa4mnYTA" name="nodeExists" returntype="//@primitivetypes.0">
          <parameter id="_mhJXUx05EfCn6vGa4mnYTA" name="pathName" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhJXVB05EfCn6vGa4mnYTA" name="removeNode"/>
        <method id="_mhJXVR05EfCn6vGa4mnYTA" name="name" returntype="//@primitivetypes.8"/>
        <method id="_mhJXVh05EfCn6vGa4mnYTA" name="absolutePath" returntype="//@primitivetypes.8"/>
        <method id="_mhJXVx05EfCn6vGa4mnYTA" name="flush"/>
        <method id="_mhJXWB05EfCn6vGa4mnYTA" name="putInt">
          <parameter id="_mhJXWR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJXWh05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.3"/>
          <parameter id="_mhJXWx05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhJXXB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJXXR05EfCn6vGa4mnYTA" name="getInt" returntype="//@primitivetypes.3">
          <parameter id="_mhJXXh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJXXx05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.3"/>
          <parameter id="_mhJXYB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-UB05EfCn6vGa4mnYTA" name="putLong">
          <parameter id="_mhJ-UR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-Uh05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.4"/>
          <parameter id="_mhJ-Ux05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhJ-VB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-VR05EfCn6vGa4mnYTA" name="getLong" returntype="//@primitivetypes.4">
          <parameter id="_mhJ-Vh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-Vx05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.4"/>
          <parameter id="_mhJ-WB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-WR05EfCn6vGa4mnYTA" name="putBoolean">
          <parameter id="_mhJ-Wh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-Wx05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.0"/>
          <parameter id="_mhJ-XB05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhJ-XR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-Xh05EfCn6vGa4mnYTA" name="getBoolean" returntype="//@primitivetypes.0">
          <parameter id="_mhJ-Xx05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-YB05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.0"/>
          <parameter id="_mhJ-YR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-Yh05EfCn6vGa4mnYTA" name="putFloat">
          <parameter id="_mhJ-Yx05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-ZB05EfCn6vGa4mnYTA" name="value" type="//@package/@subpackage.1/@classorinterface.3"/>
          <parameter id="_mhJ-ZR05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhJ-Zh05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-Zx05EfCn6vGa4mnYTA" name="getFloat" returntype="//@package/@subpackage.1/@classorinterface.3">
          <parameter id="_mhJ-aB05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-aR05EfCn6vGa4mnYTA" name="def" type="//@package/@subpackage.1/@classorinterface.3"/>
          <parameter id="_mhJ-ah05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-ax05EfCn6vGa4mnYTA" name="putDouble">
          <parameter id="_mhJ-bB05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-bR05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.7"/>
          <parameter id="_mhJ-bh05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhJ-bx05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-cB05EfCn6vGa4mnYTA" name="getDouble" returntype="//@primitivetypes.7">
          <parameter id="_mhJ-cR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-ch05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.7"/>
          <parameter id="_mhJ-cx05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-dB05EfCn6vGa4mnYTA" name="putByteArray">
          <parameter id="_mhJ-dR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-dh05EfCn6vGa4mnYTA" name="value" type="//@collectiontypes.2"/>
          <parameter id="_mhJ-dx05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhJ-eB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-eR05EfCn6vGa4mnYTA" name="getByteArray" returntype="//@collectiontypes.2">
          <parameter id="_mhJ-eh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-ex05EfCn6vGa4mnYTA" name="def" type="//@collectiontypes.2"/>
          <parameter id="_mhJ-fB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhJ-fR05EfCn6vGa4mnYTA" name="isEncrypted" returntype="//@primitivetypes.0">
          <parameter id="_mhJ-fh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhJ-fx05EfCn6vGa4mnYTA" name="internalPut">
          <parameter id="_mhJ-gB05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhJ-gR05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.8"/>
        </method>
      </classorinterface>
    </subpackage>
    <subpackage id="_mg2cUR05EfCn6vGa4mnYTA" name="datatypes">
      <classorinterface xsi:type="types:Class" id="_mg774B05EfCn6vGa4mnYTA" name="PBEKeySpec"/>
      <classorinterface xsi:type="types:Class" id="_mg8i8B05EfCn6vGa4mnYTA" name="Object"/>
      <classorinterface xsi:type="types:Class" id="_mg8i8R05EfCn6vGa4mnYTA" name="Principal"/>
      <classorinterface xsi:type="types:Class" id="_mg8i8h05EfCn6vGa4mnYTA" name="float"/>
      <classorinterface xsi:type="types:Class" id="_mg8i8x05EfCn6vGa4mnYTA" name="IPreferencesContainer"/>
      <classorinterface xsi:type="types:Class" id="_mg8i9B05EfCn6vGa4mnYTA" name="ILoginContextListener"/>
      <classorinterface xsi:type="types:Class" id="_mg8i9R05EfCn6vGa4mnYTA" name="Subject"/>
      <classorinterface xsi:type="types:Class" id="_mg8i9h05EfCn6vGa4mnYTA" name="LoginException"/>
      <classorinterface xsi:type="types:Class" id="_mg8i9x05EfCn6vGa4mnYTA" name="URL"/>
      <classorinterface xsi:type="types:Class" id="_mg8i-B05EfCn6vGa4mnYTA" name="ISecurePreferences"/>
      <classorinterface xsi:type="types:Class" id="_mg8i-R05EfCn6vGa4mnYTA" name="SecurePreferencesContainer"/>
      <classorinterface xsi:type="types:Class" id="_mg8i-h05EfCn6vGa4mnYTA" name="LoginContext"/>
      <classorinterface xsi:type="types:Class" id="_mg8i-x05EfCn6vGa4mnYTA" name="IStorageTask"/>
    </subpackage>
    <subpackage id="_mg2cUh05EfCn6vGa4mnYTA" name="interfaces">
      <classorinterface xsi:type="types:Interface" id="_mg9xEB05EfCn6vGa4mnYTA" name="IPasswordManagement">
        <method id="_mg_mQB05EfCn6vGa4mnYTA" name="setupRecovery">
          <parameter id="_mhANUB05EfCn6vGa4mnYTA" name="challengeResponse" type="//@collectiontypes.0"/>
          <parameter id="_mhCCgB05EfCn6vGa4mnYTA" name="moduleID" type="//@primitivetypes.8"/>
          <parameter id="_mhCCgR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhCCgh05EfCn6vGa4mnYTA" name="IDeleteListener">
        <method id="_mhCCgx05EfCn6vGa4mnYTA" name="onDeleted"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhCChB05EfCn6vGa4mnYTA" name="ILoginContext">
        <method id="_mhCChR05EfCn6vGa4mnYTA" name="login"/>
        <method id="_mhCChh05EfCn6vGa4mnYTA" name="logout"/>
        <method id="_mhCChx05EfCn6vGa4mnYTA" name="getSubject" returntype="//@package/@subpackage.1/@classorinterface.6"/>
        <method id="_mhCCiB05EfCn6vGa4mnYTA" name="registerListener">
          <parameter id="_mhCCiR05EfCn6vGa4mnYTA" name="listener" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_mhCCih05EfCn6vGa4mnYTA" name="unregisterListener">
          <parameter id="_mhCCix05EfCn6vGa4mnYTA" name="listener" type="//@package/@subpackage.1/@classorinterface.5"/>
        </method>
        <method id="_mhCCjB05EfCn6vGa4mnYTA" name="getLoginContext"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhCCjR05EfCn6vGa4mnYTA" name="ILoginContextListener">
        <method id="_mhCCjh05EfCn6vGa4mnYTA" name="onLoginStart">
          <parameter id="_mhCCjx05EfCn6vGa4mnYTA" name="subject" type="//@package/@subpackage.1/@classorinterface.6"/>
        </method>
        <method id="_mhCCkB05EfCn6vGa4mnYTA" name="onLoginFinish">
          <parameter id="_mhCCkR05EfCn6vGa4mnYTA" name="subject" type="//@package/@subpackage.1/@classorinterface.6"/>
          <parameter id="_mhCCkh05EfCn6vGa4mnYTA" name="loginException" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
        <method id="_mhCCkx05EfCn6vGa4mnYTA" name="onLogoutStart">
          <parameter id="_mhCClB05EfCn6vGa4mnYTA" name="subject" type="//@package/@subpackage.1/@classorinterface.6"/>
        </method>
        <method id="_mhCClR05EfCn6vGa4mnYTA" name="onLogoutFinish">
          <parameter id="_mhCClh05EfCn6vGa4mnYTA" name="subject" type="//@package/@subpackage.1/@classorinterface.6"/>
          <parameter id="_mhCClx05EfCn6vGa4mnYTA" name="logoutException" type="//@package/@subpackage.1/@classorinterface.7"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhCCmB05EfCn6vGa4mnYTA" name="IPreferencesContainer">
        <method id="_mhCCmR05EfCn6vGa4mnYTA" name="getLocation" returntype="//@package/@subpackage.1/@classorinterface.8"/>
        <method id="_mhCCmh05EfCn6vGa4mnYTA" name="getPreferences" returntype="//@package/@subpackage.1/@classorinterface.9"/>
        <method id="_mhCCmx05EfCn6vGa4mnYTA" name="hasOption" returntype="//@primitivetypes.0">
          <parameter id="_mhCCnB05EfCn6vGa4mnYTA" name="key" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
        <method id="_mhCCnR05EfCn6vGa4mnYTA" name="getOption" returntype="//@package/@subpackage.1/@classorinterface.1">
          <parameter id="_mhCCnh05EfCn6vGa4mnYTA" name="key" type="//@package/@subpackage.1/@classorinterface.1"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhCCnx05EfCn6vGa4mnYTA" name="IPrivateCredential">
        <method id="_mhCCoB05EfCn6vGa4mnYTA" name="getPrivateKey" returntype="//@package/@subpackage.1/@classorinterface.0"/>
        <method id="_mhCCoR05EfCn6vGa4mnYTA" name="getProviderID" returntype="//@primitivetypes.8"/>
        <method id="_mhCpkB05EfCn6vGa4mnYTA" name="clear"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhCpkR05EfCn6vGa4mnYTA" name="IProviderHints"/>
      <classorinterface xsi:type="types:Interface" id="_mhCpkh05EfCn6vGa4mnYTA" name="IPublicCredential">
        <method id="_mhCpkx05EfCn6vGa4mnYTA" name="getPrimaryRole" returntype="//@package/@subpackage.1/@classorinterface.2"/>
        <method id="_mhCplB05EfCn6vGa4mnYTA" name="getRoles" returntype="//@collectiontypes.1"/>
        <method id="_mhCplh05EfCn6vGa4mnYTA" name="getProviderID" returntype="//@primitivetypes.8"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhCplx05EfCn6vGa4mnYTA" name="ISecurePreferencesMain">
        <method id="_mhCpmB05EfCn6vGa4mnYTA" name="put">
          <parameter id="_mhCpmR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhCpmh05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.8"/>
          <parameter id="_mhCpmx05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhCpnB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhCpnR05EfCn6vGa4mnYTA" name="get">
          <parameter id="_mhCpnh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhCpnx05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.8"/>
          <parameter id="_mhCpoB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhCpoR05EfCn6vGa4mnYTA" name="remove">
          <parameter id="_mhCpoh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhCpox05EfCn6vGa4mnYTA" name="clear"/>
        <method id="_mhCppB05EfCn6vGa4mnYTA" name="keys" returntype="//@collectiontypes.0"/>
        <method id="_mhCppR05EfCn6vGa4mnYTA" name="childrenNames" returntype="//@collectiontypes.0"/>
        <method id="_mhCpph05EfCn6vGa4mnYTA" name="parent" returntype="//@package/@subpackage.1/@classorinterface.9"/>
        <method id="_mhCppx05EfCn6vGa4mnYTA" name="node" returntype="//@package/@subpackage.1/@classorinterface.9">
          <parameter id="_mhCpqB05EfCn6vGa4mnYTA" name="pathName" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhCpqR05EfCn6vGa4mnYTA" name="nodeExists" returntype="//@primitivetypes.0">
          <parameter id="_mhCpqh05EfCn6vGa4mnYTA" name="pathName" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhCpqx05EfCn6vGa4mnYTA" name="removeNode"/>
        <method id="_mhCprB05EfCn6vGa4mnYTA" name="name" returntype="//@primitivetypes.8"/>
        <method id="_mhCprR05EfCn6vGa4mnYTA" name="absolutePath" returntype="//@primitivetypes.8"/>
        <method id="_mhCprh05EfCn6vGa4mnYTA" name="flush"/>
        <method id="_mhDQoB05EfCn6vGa4mnYTA" name="putInt">
          <parameter id="_mhDQoR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQoh05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.3"/>
          <parameter id="_mhDQox05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhDQpB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQpR05EfCn6vGa4mnYTA" name="getInt" returntype="//@primitivetypes.3">
          <parameter id="_mhDQph05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQpx05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.3"/>
          <parameter id="_mhDQqB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQqR05EfCn6vGa4mnYTA" name="putLong">
          <parameter id="_mhDQqh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQqx05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.4"/>
          <parameter id="_mhDQrB05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhDQrR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQrh05EfCn6vGa4mnYTA" name="getLong" returntype="//@primitivetypes.4">
          <parameter id="_mhDQrx05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQsB05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.4"/>
          <parameter id="_mhDQsR05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQsh05EfCn6vGa4mnYTA" name="putBoolean">
          <parameter id="_mhDQsx05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQtB05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.0"/>
          <parameter id="_mhDQtR05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhDQth05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQtx05EfCn6vGa4mnYTA" name="getBoolean" returntype="//@primitivetypes.0">
          <parameter id="_mhDQuB05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQuR05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.0"/>
          <parameter id="_mhDQuh05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQux05EfCn6vGa4mnYTA" name="putFloat">
          <parameter id="_mhDQvB05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQvR05EfCn6vGa4mnYTA" name="value" type="//@package/@subpackage.1/@classorinterface.3"/>
          <parameter id="_mhDQvh05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhDQvx05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQwB05EfCn6vGa4mnYTA" name="getFloat" returntype="//@package/@subpackage.1/@classorinterface.3">
          <parameter id="_mhDQwR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQwh05EfCn6vGa4mnYTA" name="def" type="//@package/@subpackage.1/@classorinterface.3"/>
          <parameter id="_mhDQwx05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQxB05EfCn6vGa4mnYTA" name="putDouble">
          <parameter id="_mhDQxR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQxh05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.7"/>
          <parameter id="_mhDQxx05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhDQyB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQyR05EfCn6vGa4mnYTA" name="getDouble" returntype="//@primitivetypes.7">
          <parameter id="_mhDQyh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQyx05EfCn6vGa4mnYTA" name="def" type="//@primitivetypes.7"/>
          <parameter id="_mhDQzB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhDQzR05EfCn6vGa4mnYTA" name="putByteArray">
          <parameter id="_mhDQzh05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhDQzx05EfCn6vGa4mnYTA" name="value" type="//@collectiontypes.2"/>
          <parameter id="_mhDQ0R05EfCn6vGa4mnYTA" name="encrypt" type="//@primitivetypes.0"/>
          <parameter id="_mhDQ0h05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhD3sB05EfCn6vGa4mnYTA" name="getByteArray" returntype="//@collectiontypes.2">
          <parameter id="_mhD3sR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhD3sh05EfCn6vGa4mnYTA" name="def" type="//@collectiontypes.2"/>
          <parameter id="_mhD3sx05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.10"/>
        </method>
        <method id="_mhD3tB05EfCn6vGa4mnYTA" name="isEncrypted" returntype="//@primitivetypes.0">
          <parameter id="_mhD3tR05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhD3th05EfCn6vGa4mnYTA" name="internalPut">
          <parameter id="_mhD3tx05EfCn6vGa4mnYTA" name="key" type="//@primitivetypes.8"/>
          <parameter id="_mhD3uB05EfCn6vGa4mnYTA" name="value" type="//@primitivetypes.8"/>
        </method>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhD3uR05EfCn6vGa4mnYTA" name="IStorageConstants"/>
      <classorinterface xsi:type="types:Interface" id="_mhD3uh05EfCn6vGa4mnYTA" name="IStorageTask">
        <method id="_mhD3ux05EfCn6vGa4mnYTA" name="execute"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhD3vB05EfCn6vGa4mnYTA" name="IUICallbacks">
        <method id="_mhD3vR05EfCn6vGa4mnYTA" name="setupPasswordRecovery">
          <parameter id="_mhD3vh05EfCn6vGa4mnYTA" name="size" type="//@primitivetypes.3"/>
          <parameter id="_mhD3vx05EfCn6vGa4mnYTA" name="moduleID" type="//@primitivetypes.8"/>
          <parameter id="_mhD3wB05EfCn6vGa4mnYTA" name="container" type="//@package/@subpackage.1/@classorinterface.4"/>
        </method>
        <method id="_mhD3wR05EfCn6vGa4mnYTA" name="ask" returntype="//@primitivetypes.0">
          <parameter id="_mhD3wh05EfCn6vGa4mnYTA" name="msg" type="//@primitivetypes.8"/>
        </method>
        <method id="_mhD3wx05EfCn6vGa4mnYTA" name="execute">
          <parameter id="_mhD3xB05EfCn6vGa4mnYTA" name="callback" type="//@package/@subpackage.1/@classorinterface.12"/>
        </method>
        <method id="_mhD3xR05EfCn6vGa4mnYTA" name="runningUI" returntype="//@primitivetypes.0"/>
      </classorinterface>
      <classorinterface xsi:type="types:Interface" id="_mhD3xh05EfCn6vGa4mnYTA" name="IValidatingPasswordProvider">
        <method id="_mhD3xx05EfCn6vGa4mnYTA" name="isValid" returntype="//@primitivetypes.0"/>
      </classorinterface>
    </subpackage>
  </package>
</java:JavaRoot>
