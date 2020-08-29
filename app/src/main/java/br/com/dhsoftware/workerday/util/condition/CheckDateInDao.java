package br.com.dhsoftware.workerday.util.condition;

import br.com.dhsoftware.workerday.dao.Dao;

public class CheckDateInDao implements CheckCondition {

    private Dao dao;

    public CheckDateInDao(Dao dao) {
        this.dao = dao;
    }


    @Override
    public boolean condition(Object object) {
        String date = (String) object;
        return dao.isDateNotSet(date);
    }
}
