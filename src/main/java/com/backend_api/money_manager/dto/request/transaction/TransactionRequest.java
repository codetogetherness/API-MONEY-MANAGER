package com.backend_api.money_manager.dto.request.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequest {
    private String amount; //HistoryTransaction.amount;
    private String categoryId; //HistoryTransaction.categoryId;
    private String categoryAction; //HistoryTransaction.categoryAction
    private String name; //this name for image name
    private String image; //HistoryTransaction.image
    private String ext;
    private String description;
    private String title; //HistoryTransaction.title


    public String getFileName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public String getBase64() {
        return image;
    }

}
