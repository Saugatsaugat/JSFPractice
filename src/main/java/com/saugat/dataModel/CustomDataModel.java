/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.saugat.dataModel;

import Entities.FutsalSchedule;
import Model.AbstractEntity;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class CustomDataModel<T extends AbstractEntity> extends ListDataModel<T> implements SelectableDataModel<T> {

    private Class<T> entityClass;

    public CustomDataModel(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public CustomDataModel() {
    }

    public CustomDataModel(List<T> data) {
        super(data);
    }

    @Override
    public Object getRowKey(T object) {
        T selectedEntity = null;
        Long id = null;
        try {
            selectedEntity = entityClass.newInstance();
            id = selectedEntity.getId();
            return id;
        } catch (Exception e) {

        }
        return id;
    }

    public T getRowData(String rowKey) {
        List<T> dataList = (List<T>) getWrappedData();
        for (T data : dataList) {
            if (getRowKey(data).equals(rowKey)) {
                return data;
            }
        }
        return null;
    }
}
