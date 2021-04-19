package pl.damian.beautyglow.dao;

import pl.damian.beautyglow.entity.UsersTreatments;

import java.util.Date;
import java.util.List;

public interface UsersTreatmentsDao {
    public void addUsersTreatments(UsersTreatments usersTreatments);

    public void deleteUsersTreatments(int id);

    public void updateUsersTreatments(UsersTreatments usersTreatments);

    public UsersTreatments getUsersTreatmentsById(int id);

    public List<UsersTreatments> getUsersTreatments();

    public List<UsersTreatments> getUsersTreatmentsOnSpecificDay(Date date);

}
