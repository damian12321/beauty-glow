package pl.damian.beautyglow.dao;

import pl.damian.beautyglow.entity.Form;

public interface FormDao {
    public Form findFormByUserId(int userId);
}
