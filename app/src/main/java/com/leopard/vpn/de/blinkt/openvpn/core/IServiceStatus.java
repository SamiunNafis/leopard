/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /storage/emulated/0/VPN2018/app/src/main/aidl/de/blinkt/openvpn/core/IServiceStatus.aidl
 */
package de.blinkt.openvpn.core;
public interface IServiceStatus extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements de.blinkt.openvpn.core.IServiceStatus
{
private static final java.lang.String DESCRIPTOR = "de.blinkt.openvpn.core.IServiceStatus";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an de.blinkt.openvpn.core.IServiceStatus interface,
 * generating a proxy if needed.
 */
public static de.blinkt.openvpn.core.IServiceStatus asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof de.blinkt.openvpn.core.IServiceStatus))) {
return ((de.blinkt.openvpn.core.IServiceStatus)iin);
}
return new de.blinkt.openvpn.core.IServiceStatus.Stub.Proxy(obj);
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
case TRANSACTIONregisterStatusCallback:
{
data.enforceInterface(descriptor);
de.blinkt.openvpn.core.IStatusCallbacks arg0;
arg0 = de.blinkt.openvpn.core.IStatusCallbacks.Stub.asInterface(data.readStrongBinder());
android.os.ParcelFileDescriptor result = this.registerStatusCallback(arg0);
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
case TRANSACTIONunregisterStatusCallback:
{
data.enforceInterface(descriptor);
de.blinkt.openvpn.core.IStatusCallbacks arg0;
arg0 = de.blinkt.openvpn.core.IStatusCallbacks.Stub.asInterface(data.readStrongBinder());
this.unregisterStatusCallback(arg0);
reply.writeNoException();
return true;
}
case TRANSACTIONgetLastConnectedVPN:
{
data.enforceInterface(descriptor);
java.lang.String result = this.getLastConnectedVPN();
reply.writeNoException();
reply.writeString(result);
return true;
}
case TRANSACTIONsetCachedPassword:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
int arg1;
arg1 = data.readInt();
java.lang.String arg2;
arg2 = data.readString();
this.setCachedPassword(arg0, arg1, arg2);
reply.writeNoException();
return true;
}
case TRANSACTIONgetTrafficHistory:
{
data.enforceInterface(descriptor);
de.blinkt.openvpn.core.TrafficHistory result = this.getTrafficHistory();
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
private static class Proxy implements de.blinkt.openvpn.core.IServiceStatus
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
/**
          * Registers to receive OpenVPN Status Updates and gets a
          * ParcelFileDescript back that contains the log up to that point
          */
@Override public android.os.ParcelFileDescriptor registerStatusCallback(de.blinkt.openvpn.core.IStatusCallbacks cb) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
android.os.ParcelFileDescriptor result;
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTIONregisterStatusCallback, data, reply, 0);
reply.readException();
if ((0!=reply.readInt())) {
result = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(reply);
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
/**
           * Remove a previously registered callback interface.
           */
@Override public void unregisterStatusCallback(de.blinkt.openvpn.core.IStatusCallbacks cb) throws android.os.RemoteException
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
@Override public java.lang.String getLastConnectedVPN() throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
java.lang.String result;
try {
data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTIONgetLastConnectedVPN, data, reply, 0);
reply.readException();
result = reply.readString();
}
finally {
reply.recycle();
data.recycle();
}
return result;
}
/**
          * Sets a cached password
          */
@Override public void setCachedPassword(java.lang.String uuid, int type, java.lang.String password) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(uuid);
data.writeInt(type);
data.writeString(password);
mRemote.transact(Stub.TRANSACTIONsetCachedPassword, data, reply, 0);
reply.readException();
}
finally {
reply.recycle();
data.recycle();
}
}
/**
       * Gets the traffic history
       */
@Override public de.blinkt.openvpn.core.TrafficHistory getTrafficHistory() throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
android.os.Parcel reply = android.os.Parcel.obtain();
de.blinkt.openvpn.core.TrafficHistory result;
try {
data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTIONgetTrafficHistory, data, reply, 0);
reply.readException();
if ((0!=reply.readInt())) {
result = de.blinkt.openvpn.core.TrafficHistory.CREATOR.createFromParcel(reply);
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
static final int TRANSACTIONregisterStatusCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTIONunregisterStatusCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTIONgetLastConnectedVPN = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTIONsetCachedPassword = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTIONgetTrafficHistory = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
/**
          * Registers to receive OpenVPN Status Updates and gets a
          * ParcelFileDescript back that contains the log up to that point
          */
public android.os.ParcelFileDescriptor registerStatusCallback(de.blinkt.openvpn.core.IStatusCallbacks cb) throws android.os.RemoteException;
/**
           * Remove a previously registered callback interface.
           */
public void unregisterStatusCallback(de.blinkt.openvpn.core.IStatusCallbacks cb) throws android.os.RemoteException;
public java.lang.String getLastConnectedVPN() throws android.os.RemoteException;
/**
          * Sets a cached password
          */
public void setCachedPassword(java.lang.String uuid, int type, java.lang.String password) throws android.os.RemoteException;
/**
       * Gets the traffic history
       */
public de.blinkt.openvpn.core.TrafficHistory getTrafficHistory() throws android.os.RemoteException;
}