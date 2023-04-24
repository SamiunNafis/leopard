/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /storage/emulated/0/VPN2018/app/src/main/aidl/de/blinkt/openvpn/core/IStatusCallbacks.aidl
 */
package de.blinkt.openvpn.core;
public interface IStatusCallbacks extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements de.blinkt.openvpn.core.IStatusCallbacks
{
private static final java.lang.String DESCRIPTOR = "de.blinkt.openvpn.core.IStatusCallbacks";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an de.blinkt.openvpn.core.IStatusCallbacks interface,
 * generating a proxy if needed.
 */
public static de.blinkt.openvpn.core.IStatusCallbacks asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof de.blinkt.openvpn.core.IStatusCallbacks))) {
return ((de.blinkt.openvpn.core.IStatusCallbacks)iin);
}
return new de.blinkt.openvpn.core.IStatusCallbacks.Stub.Proxy(obj);
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
case TRANSACTIONnewLogItem:
{
data.enforceInterface(descriptor);
de.blinkt.openvpn.core.LogItem arg0;
if ((0!=data.readInt())) {
arg0 = de.blinkt.openvpn.core.LogItem.CREATOR.createFromParcel(data);
}
else {
arg0 = null;
}
this.newLogItem(arg0);
return true;
}
case TRANSACTIONupdateStateString:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
java.lang.String arg1;
arg1 = data.readString();
int arg2;
arg2 = data.readInt();
de.blinkt.openvpn.core.ConnectionStatus arg3;
if ((0!=data.readInt())) {
arg3 = de.blinkt.openvpn.core.ConnectionStatus.CREATOR.createFromParcel(data);
}
else {
arg3 = null;
}
this.updateStateString(arg0, arg1, arg2, arg3);
return true;
}
case TRANSACTIONupdateByteCount:
{
data.enforceInterface(descriptor);
long arg0;
arg0 = data.readLong();
long arg1;
arg1 = data.readLong();
this.updateByteCount(arg0, arg1);
return true;
}
case TRANSACTIONconnectedVPN:
{
data.enforceInterface(descriptor);
java.lang.String arg0;
arg0 = data.readString();
this.connectedVPN(arg0);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements de.blinkt.openvpn.core.IStatusCallbacks
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
     * Called when the service has a new status for you.
     */
@Override public void newLogItem(de.blinkt.openvpn.core.LogItem item) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
if ((item!=null)) {
data.writeInt(1);
item.writeToParcel(data, 0);
}
else {
data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTIONnewLogItem, data, null, android.os.IBinder.FLAGONEWAY);
}
finally {
data.recycle();
}
}
@Override public void updateStateString(java.lang.String state, java.lang.String msg, int resid, de.blinkt.openvpn.core.ConnectionStatus level) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(state);
data.writeString(msg);
data.writeInt(resid);
if ((level!=null)) {
data.writeInt(1);
level.writeToParcel(data, 0);
}
else {
data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTIONupdateStateString, data, null, android.os.IBinder.FLAGONEWAY);
}
finally {
data.recycle();
}
}
@Override public void updateByteCount(long inBytes, long outBytes) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeLong(inBytes);
data.writeLong(outBytes);
mRemote.transact(Stub.TRANSACTIONupdateByteCount, data, null, android.os.IBinder.FLAGONEWAY);
}
finally {
data.recycle();
}
}
@Override public void connectedVPN(java.lang.String uuid) throws android.os.RemoteException
{
android.os.Parcel data = android.os.Parcel.obtain();
try {
data.writeInterfaceToken(DESCRIPTOR);
data.writeString(uuid);
mRemote.transact(Stub.TRANSACTIONconnectedVPN, data, null, android.os.IBinder.FLAGONEWAY);
}
finally {
data.recycle();
}
}
}
static final int TRANSACTIONnewLogItem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTIONupdateStateString = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTIONupdateByteCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTIONconnectedVPN = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
/**
     * Called when the service has a new status for you.
     */
public void newLogItem(de.blinkt.openvpn.core.LogItem item) throws android.os.RemoteException;
public void updateStateString(java.lang.String state, java.lang.String msg, int resid, de.blinkt.openvpn.core.ConnectionStatus level) throws android.os.RemoteException;
public void updateByteCount(long inBytes, long outBytes) throws android.os.RemoteException;
public void connectedVPN(java.lang.String uuid) throws android.os.RemoteException;
}