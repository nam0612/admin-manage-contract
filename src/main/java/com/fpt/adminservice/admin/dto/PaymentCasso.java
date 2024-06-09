package com.fpt.adminservice.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCasso {
    public Integer Id;
    public String When;
    public Double Amount;
    public String Description;
    public int CusumBalance;
    public String Tid;
    public String SubAccountId;
    public String BankSubAccountId;
    public String VirtualAccount;
    public String VirtualAccountName;
    public String CorresponsiveName;
    public String CorresponsiveAccount;
    public String CorresponsiveBankId;
    public String CorresponsiveBankName;
}
