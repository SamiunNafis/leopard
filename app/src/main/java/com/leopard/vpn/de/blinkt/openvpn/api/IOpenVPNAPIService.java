/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /storage/emulated/0/VPN2018/app/src/main/aidl/de/blinkt/openvpn/api/IOpenVPNAPIService.aidl
 */
package de.blinkt.openvpn.api;
public interface IOpenVPNAPIService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements de.blinkt.openvpn.api.IOpenVPNAPIService
{
private static final java.lang.String DESCRIPTOR = "de.blinkt.openvpn.api.IOpenVPNAPIService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an de.blinkt.openvpn.api.IOpenVPNAPIService interface,
 * generating a proxy if needed.
 */
public static de.blinkt.openvpn.api.IOpenVPNAPIService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof de.blinkt.openvpn.api.IOpenVPNAPIService))) {
return ((de.blinkt.openvpn.api.IOpenVPNAPIService)iin);
}
return new de.blinkt.openvpn.api.IOpenVPNAPIService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACETRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTIONgetProfiles:
{
data.enforceInterface(descriptor);
java.util.List<de.blinkt.openvpn.api.APIVpnProfile> result = this.getProfiles();
reply.writeNoException();
reply.writeTypedList(result);
return true;
}
case TRANSACTIONstartProfile:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
this.startProfile(arg0);
reply.writeNoException();
return true;
}
case TRANSACTIONaddVPNProfile:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
java.lang.String arg1;
arg1 = data.readString();
boolean result = this.addVPNProfile(arg0, arg1);
reply.writeNoException();
reply.writeInt(((result)?(1):(0)));
return true;
}
case TRANSACTIONstartVPN:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
this.startVPN(arg0);
reply.writeNoException();
return true;
}
case TRANSACTIONprepare:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
android.content.Intent result = this.prepare(arg0);
reply.writeNoException();
if ((result!=null)) {
reply.writeInt(1);
result.writeToParcel(reply, android.os.Parcelable.PARCELABLEWRITERETURNVALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTIONprepareVPNService:
{
data.enforceInterface(descriptor);
android.content.Intent result = this.prepareVPNService();
reply.writeNoException();
if ((result!=null)) {
reply.writeInt(1);
result.writeToParcel(reply, android.os.Parcelable.PARCELABLEWRITERETURNVALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTIONdisconnect:
{
data.enforceInterface(descriptor);
this.disconnect();
reply.writeNoException();
return true;
}
case TRANSACTIONpause:
{
data.enforceInterface(descriptor);
this.pause();
reply.writeNoException();
return true;
}
case TRANSACTIONresume:
{
data.enforceInterface(descriptor);
this.resume();
reply.writeNoException();
return true;
}
case TRANSACTIONregisterStatusCallback:
{
data.enforceInterface(descriptor);
de.blinkt.openvpn.api.IOpenVPNStatusCallback arg0;
arg0 = de.blinkt.openvpn.api.IOpenVPNStatusCallback.Stub.asInterface(data.readStrongBinder());
this.registerStatusCallback(arg0);
reply.writeNoException();
return true;
}
case TRANSACTIONunregisterStatusCallback:
{
data.enforceInterface(descriptor);
de.blinkt.openvpn.api.IOpenVPNStatusCallback arg0;
arg0 = de.blinkt.openvpn.api.IOpenVPNStatusCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterStatusCallback(arg0);
reply.writeNoException();
return true;
}
case TRANSACTIONremoveProfile:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
this.removeProfile(arg0);
reply.writeNoException();
return true;
}
case TRANSACTIONprotectSocket:
{
data.enforceInterface(descriptor);
android.os.ParcelFileDescriptor arg0;
if ((0!=data.readInt())) {
arg0 = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(data);
}
else {
arg0 = null;
}
boolean result = this.protectSocket(arg0);
reply.writeNoException();
reply.writeInt(((result)?(1):(0)));
return true;
}
case TRANSACTIONaddNewVPNProfile:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
boolean arg1;
arg1 = (0!=data.readInt());
java.lang.String arg2;
arg2 = data.readString();
de.blinkt.openvpn.api.APIVpnProfile result = this.addNewVPNProfile(arg0, arg1, arg2);
reply.writeNoException();
if ((result!=null)) {
reply.writeInt(1);
result.writeToParcel(reply, android.os.Parcelable.PARCELABLEWRITERETURNVALUE);
}
else {
reply.writeInt(0);
}
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements de.blinkt.openvpn.api.IOpenVPNAPIService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.util.List<de.blinkt.openvpn.api.APIVpnProfile> getProfiles() throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
java.util.List<de.blinkt.openvpn.api.APIVpnProfile> result;
try {
data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTIONgetProfiles, data, reply, 0);
reply.readException();
result = reply.createTypedArrayList(de.blinkt.openvpn.api.APIVpnProfile.CREATOR);
}
finally {
reply.recycle();
data.recycle();
}
return result;
}
@Override public void startProfile(java.lang.String profileUUID) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(profileUUID);
mRemote.transact(Stub.TRANSACTIONstartProfile, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/** Use a profile with all certificates etc. embedded,
	 * old version which does not return the UUID of the addded profile, see
	 * below for a version that return the UUID on add */
@Override public boolean addVPNProfile(java.lang.String name, java.lang.String config) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
boolean result;
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(name);
data.writeString(config);
mRemote.transact(Stub.TRANSACTIONaddVPNProfile, data, reply, 0);
reply.readException();
result = (0!=reply.readInt());
}
finally {
reply.recycle();
data.recycle();
}
return result;
}
/** start a profile using a config as inline string. Make sure that all needed data is inlined,
	 * e.g., using <ca>...</ca> or <auth-user-data>...</auth-user-data>
	 * See the OpenVPN manual page for more on inlining files */
@Override public void startVPN(java.lang.String inlineconfig) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(inlineconfig);
mRemote.transact(Stub.TRANSACTIONstartVPN, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/** This permission framework is used  to avoid confused deputy style attack to the VPN
	 * calling this will give null if the app is allowed to use the external API and an Intent
	 * that can be launched to request permissions otherwise */
@Override public android.content.Intent prepare(java.lang.String packagename) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
android.content.Intent result;
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(packagename);
mRemote.transact(Stub.TRANSACTIONprepare, data, reply, 0);
reply.readException();
if ((0!=reply.readInt())) {
result = android.content.Intent.CREATOR.createFromParcel(reply);
}
else {
result = null;
}
}
finally {
reply.recycle();
data.recycle();
}
return result;
}
/** Used to trigger to the Android VPN permission dialog (VPNService.prepare()) in advance,
	 * if this return null OpenVPN for ANdroid already has the permissions otherwise you can start the returned Intent
	 * to let OpenVPN for Android request the permission */
@Override public android.content.Intent prepareVPNService() throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
android.content.Intent result;
try {
data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTIONprepareVPNService, data, reply, 0);
reply.readException();
if ((0!=reply.readInt())) {
result = android.content.Intent.CREATOR.createFromParcel(reply);
}
else {
result = null;
}
}
finally {
reply.recycle();
data.recycle();
}
return result;
}
/* Disconnect the VPN */
@Override public void disconnect() throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTIONdisconnect, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/* Pause the VPN (same as using the pause feature in the notifcation bar) */
@Override public void pause() throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTIONpause, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/* Resume the VPN (same as using the pause feature in the notifcation bar) */
@Override public void resume() throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTIONresume, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/**
      * Registers to receive OpenVPN Status Updates
      */
@Override public void registerStatusCallback(de.blinkt.openvpn.api.IOpenVPNStatusCallback cb) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTIONregisterStatusCallback, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/**
     * Remove a previously registered callback interface.
     */
@Override public void unregisterStatusCallback(de.blinkt.openvpn.api.IOpenVPNStatusCallback cb) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTIONunregisterStatusCallback, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/** Remove a profile by UUID */
@Override public void removeProfile(java.lang.String profileUUID) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(profileUUID);
mRemote.transact(Stub.TRANSACTIONremoveProfile, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/** Request a socket to be protected as a VPN socket would be. Useful for creating
	  * a helper socket for an app controlling OpenVPN
	  * Before calling this function you should make sure OpenVPN for Android may actually
	  * this function by checking if prepareVPNService returns null; */
@Override public boolean protectSocket(android.os.ParcelFileDescriptor fd) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
boolean result;
try {
data.writeInterfaceToken(DESCRIPTOR);
if ((fd!=null)) {
data.writeInt(1);
fd.writeToParcel(data, 0);
}
else {
data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTIONprotectSocket, data, reply, 0);
reply.readException();
result = (0!=reply.readInt());
}
finally {
reply.recycle();
data.recycle();
}
return result;
}
/** Use a profile with all certificates etc. embedded */
@Override public de.blinkt.openvpn.api.APIVpnProfile addNewVPNProfile(java.lang.String name, boolean userEditable, java.lang.String config) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
de.blinkt.openvpn.api.APIVpnProfile result;
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(name);
data.writeInt(((userEditable)?(1):(0)));
data.writeString(config);
mRemote.transact(Stub.TRANSACTIONaddNewVPNProfile, data, reply, 0);
reply.readException();
if ((0!=reply.readInt())) {
result = de.blinkt.openvpn.api.APIVpnProfile.CREATOR.createFromParcel(reply);
}
else {
result = null;
}
}
finally {
reply.recycle();
data.recycle();
}
return result;
}
}
static final int TRANSACTIONgetProfiles = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTIONstartProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTIONaddVPNProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTIONstartVPN = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTIONprepare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTIONprepareVPNService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTIONdisconnect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTIONpause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTIONresume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTIONregisterStatusCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTIONunregisterStatusCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTIONremoveProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTIONprotectSocket = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTIONaddNewVPNProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
public java.util.List<de.blinkt.openvpn.api.APIVpnProfile> getProfiles() throws android.os.RemoteException;
public void startProfile(java.lang.String profileUUID) throws android.os.RemoteException;
/** Use a profile with all certificates etc. embedded,
	 * old version which does not return the UUID of the addded profile, see
	 * below for a version that return the UUID on add */
public boolean addVPNProfile(java.lang.String name, java.lang.String config) throws android.os.RemoteException;
/** start a profile using a config as inline string. Make sure that all needed data is inlined,
	 * e.g., using <ca>...</ca> or <auth-user-data>...</auth-user-data>
	 * See the OpenVPN manual page for more on inlining files */
public void startVPN(java.lang.String inlineconfig) throws android.os.RemoteException;
/** This permission framework is used  to avoid confused deputy style attack to the VPN
	 * calling this will give null if the app is allowed to use the external API and an Intent
	 * that can be launched to request permissions otherwise */
public android.content.Intent prepare(java.lang.String packagename) throws android.os.RemoteException;
/** Used to trigger to the Android VPN permission dialog (VPNService.prepare()) in advance,
	 * if this return null OpenVPN for ANdroid already has the permissions otherwise you can start the returned Intent
	 * to let OpenVPN for Android request the permission */
public android.content.Intent prepareVPNService() throws android.os.RemoteException;
/* Disconnect the VPN */
public void disconnect() throws android.os.RemoteException;
/* Pause the VPN (same as using the pause feature in the notifcation bar) */
public void pause() throws android.os.RemoteException;
/* Resume the VPN (same as using the pause feature in the notifcation bar) */
public void resume() throws android.os.RemoteException;
/**
      * Registers to receive OpenVPN Status Updates
      */
public void registerStatusCallback(de.blinkt.openvpn.api.IOpenVPNStatusCallback cb) throws android.os.RemoteException;
/**
     * Remove a previously registered callback interface.
     */
public void unregisterStatusCallback(de.blinkt.openvpn.api.IOpenVPNStatusCallback cb) throws android.os.RemoteException;
/** Remove a profile by UUID */
public void removeProfile(java.lang.String profileUUID) throws android.os.RemoteException;
/** Request a socket to be protected as a VPN socket would be. Useful for creating
	  * a helper socket for an app controlling OpenVPN
	  * Before calling this function you should make sure OpenVPN for Android may actually
	  * this function by checking if prepareVPNService returns null; */
public boolean protectSocket(android.os.ParcelFileDescriptor fd) throws android.os.RemoteException;
/** Use a profile with all certificates etc. embedded */
public de.blinkt.openvpn.api.APIVpnProfile addNewVPNProfile(java.lang.String name, boolean userEditable, java.lang.String config) throws android.os.RemoteException;
}