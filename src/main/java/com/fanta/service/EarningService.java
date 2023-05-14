package com.fanta.service;

import com.fanta.dao.EarningDAO;
import com.fanta.entity.Earning;

import java.util.List;

public class EarningService implements ServiceInterface<Earning> {
    private EarningDAO earningDAO;

    public EarningService() {
        earningDAO = new EarningDAO();
    }

    @Override
    public Earning getById(Long earningId) {
        return earningDAO.findById(earningId);
    }

    @Override
    public List<Earning> getAll() {
        return earningDAO.findAll();
    }

    @Override
    public void save(Earning earning) {
        earningDAO.save(earning);
    }

    @Override
    public void update(Long earningId, Earning earning) {
        earningDAO.update(earningId, earning);
    }

    @Override
    public void delete(Long earningId) {
        earningDAO.delete(earningId);
    }
}

