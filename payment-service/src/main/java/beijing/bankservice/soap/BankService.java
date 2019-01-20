/**
 * BankService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package beijing.bankservice.soap;

public interface BankService extends java.rmi.Remote {
    public beijing.bankservice.model.Account getAccount(java.lang.String arg0) throws java.rmi.RemoteException, beijing.bankservice.exception.BankServiceException;
    public beijing.bankservice.model.Account getAccountByCprNumber(java.lang.String arg0) throws java.rmi.RemoteException, beijing.bankservice.exception.BankServiceException;
    public java.lang.String createAccountWithBalance(beijing.bankservice.model.User arg0, java.math.BigDecimal arg1) throws java.rmi.RemoteException, beijing.bankservice.exception.BankServiceException;
    public void retireAccount(java.lang.String arg0) throws java.rmi.RemoteException, beijing.bankservice.exception.BankServiceException;
    public beijing.bankservice.model.AccountInfo[] getAccounts() throws java.rmi.RemoteException;
    public void transferMoneyFromTo(java.lang.String arg0, java.lang.String arg1, java.math.BigDecimal arg2, java.lang.String arg3) throws java.rmi.RemoteException, beijing.bankservice.exception.BankServiceException;
}
