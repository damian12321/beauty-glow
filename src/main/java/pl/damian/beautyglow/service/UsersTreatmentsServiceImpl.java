package pl.damian.beautyglow.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.damian.beautyglow.dao.UsersTreatmentsDao;
import pl.damian.beautyglow.entity.UsersTreatments;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UsersTreatmentsServiceImpl implements UsersTreatmentsService {
    @Autowired
    private UsersTreatmentsDao usersTreatmentsDao;

    @Override
    public void addUsersTreatments(UsersTreatments userTreatments) {
        usersTreatmentsDao.addUsersTreatments(userTreatments);
    }

    @Override
    public void deleteUsersTreatments(int id) {
        usersTreatmentsDao.deleteUsersTreatments(id);
    }

    @Override
    public void updateUsersTreatments(UsersTreatments userTreatments) {
        usersTreatmentsDao.updateUsersTreatments(userTreatments);
    }

    @Override
    public UsersTreatments getUsersTreatmentsById(int id) {
        return usersTreatmentsDao.getUsersTreatmentsById(id);
    }

    @Override
    public List<UsersTreatments> getUsersTreatments() {
        return usersTreatmentsDao.getUsersTreatments();
    }

    @Override
    public List<UsersTreatments> getUsersTreatmentsOnSpecificDay(Date date) {
        return usersTreatmentsDao.getUsersTreatmentsOnSpecificDay(date);
    }
}
