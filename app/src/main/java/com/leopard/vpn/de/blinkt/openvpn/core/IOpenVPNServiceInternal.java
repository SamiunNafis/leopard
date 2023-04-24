/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /storage/emulated/0/VPN2018/app/src/main/aidl/de/blinkt/openvpn/core/IOpenVPNServiceInternal.aidl
 */
package de.blinkt.openvpn.core;
/**
 * Created by arne on 15.11.16.
 */
public interface IOpenVPNServiceInternal extends android.os.IInterface {
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements de.blinkt.openvpn.core.IOpenVPNServiceInternal {
        private static final java.lang.String DESCRIPTOR = "de.blinkt.openvpn.core.IOpenVPNServiceInternal";
        /** Construct the stub at attach it to the interface. */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an de.blinkt.openvpn.core.IOpenVPNServiceInternal interface,
         * generating a proxy if needed.
         */
        public static de.blinkt.openvpn.core.IOpenVPNServiceInternal asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof de.blinkt.openvpn.core.IOpenVPNServiceInternal))) {
                return ((de.blinkt.openvpn.core.IOpenVPNServiceInternal)iin);
            }
            return new de.blinkt.openvpn.core.IOpenVPNServiceInternal.Stub.Proxy(obj);
        }
        @Override public android.os.IBinder asBinder() {
            return this;
        }
        @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION:
                    {
                        reply.writeString(descriptor);
                        return true;
                    }
                case TRANSACTIONprotect:
                    {
                        data.enforceInterface(descriptor);
                        int arg0;
                        arg0 = data.readInt();
                        boolean result = this.protect(arg0);
                        reply.writeNoException();
                        reply.writeInt(((result) ?(1): (0)));
                        return true;
                    }
                case TRANSACTIONuserPause:
                    {
                        data.enforceInterface(descriptor);
                        boolean arg0;
                        arg0 = (0 != data.readInt());
                        this.userPause(arg0);
                        reply.writeNoException();
                        return true;
                    }
                case TRANSACTIONstopVPN:
                    {
                        data.enforceInterface(descriptor);
                        boolean arg0;
                        arg0 = (0 != data.readInt());
                        boolean result = this.stopVPN(arg0);
                        reply.writeNoException();
                        reply.writeInt(((result) ?(1): (0)));
                        return true;
                    }
                default:
                    {
                        return super.onTransact(code, data, reply, flags);
                    }
            }
        }
        private static class Proxy implements de.blinkt.openvpn.core.IOpenVPNServiceInternal {
            private android.os.IBinder mRemote;
            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }
            @Override public android.os.IBinder asBinder() {
                return mRemote;
            }
            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }
            @Override public boolean protect(int fd) throws android.os.RemoteException {
                android.os.Parcel data = android.os.Parcel.obtain();
                android.os.Parcel reply = android.os.Parcel.obtain();
                boolean result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(fd);
                    mRemote.transact(Stub.TRANSACTIONprotect, data, reply, 0);
                    reply.readException();
                    result = (0 != reply.readInt());
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return result;
            }
            @Override public void userPause(boolean b) throws android.os.RemoteException {
                android.os.Parcel data = android.os.Parcel.obtain();
                android.os.Parcel reply = android.os.Parcel.obtain();
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(((b) ?(1): (0)));
                    mRemote.transact(Stub.TRANSACTIONuserPause, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
            /**
             * @param replaceConnection True if the VPN is connected by a new connection.
             * @return true if there was a process that has been send a stop signal
             */
            @Override public boolean stopVPN(boolean replaceConnection) throws android.os.RemoteException {
                android.os.Parcel data = android.os.Parcel.obtain();
                android.os.Parcel reply = android.os.Parcel.obtain();
                boolean result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(((replaceConnection) ?(1): (0)));
                    mRemote.transact(Stub.TRANSACTIONstopVPN, data, reply, 0);
                    reply.readException();
                    result = (0 != reply.readInt());
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return result;
            }
        }
        static final int TRANSACTIONprotect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTIONuserPause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTIONstopVPN = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    }
    public boolean protect(int fd) throws android.os.RemoteException;
    public void userPause(boolean b) throws android.os.RemoteException;
    /**
     * @param replaceConnection True if the VPN is connected by a new connection.
     * @return true if there was a process that has been send a stop signal
     */
    public boolean stopVPN(boolean replaceConnection) throws android.os.RemoteException;
}
