/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\20140105_Android\\Workspace\\24.RemoteActivity\\src\\com\\itheima\\remoteservice\\invokeinterface\\InvokeInterface.aidl
 */
package com.itheima.remoteservice.invokeinterface;
public interface InvokeInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.itheima.remoteservice.invokeinterface.InvokeInterface
{
private static final java.lang.String DESCRIPTOR = "com.itheima.remoteservice.invokeinterface.InvokeInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.itheima.remoteservice.invokeinterface.InvokeInterface interface,
 * generating a proxy if needed.
 */
public static com.itheima.remoteservice.invokeinterface.InvokeInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.itheima.remoteservice.invokeinterface.InvokeInterface))) {
return ((com.itheima.remoteservice.invokeinterface.InvokeInterface)iin);
}
return new com.itheima.remoteservice.invokeinterface.InvokeInterface.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_pay:
{
data.enforceInterface(DESCRIPTOR);
com.itheima.removeservice.bean.Person _arg0;
if ((0!=data.readInt())) {
_arg0 = com.itheima.removeservice.bean.Person.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
boolean _result = this.pay(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.itheima.remoteservice.invokeinterface.InvokeInterface
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
@Override public boolean pay(com.itheima.removeservice.bean.Person p, int amount) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((p!=null)) {
_data.writeInt(1);
p.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(amount);
mRemote.transact(Stub.TRANSACTION_pay, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_pay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public boolean pay(com.itheima.removeservice.bean.Person p, int amount) throws android.os.RemoteException;
}
