package service;

import entity.Data;
import java.util.List;

public interface iDataServiceDao {
    void save(Data data);
    List<Data> findAll();
}
