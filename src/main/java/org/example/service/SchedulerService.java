package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Scheduler;
import org.example.repo.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SchedulerService {
    private SchedulerRepository schedulerRepository;

    @Autowired
    public SchedulerService(SchedulerRepository schedulerRepository) {

        this.schedulerRepository = schedulerRepository;
    }

    public Optional<Scheduler> findById(long id){

        return schedulerRepository.findById(id);
    }

    @Transactional
    public boolean create(Scheduler scheduler) {
        try {
            log.info("Save scheduler");
            schedulerRepository.save(scheduler);
            try {
                //emailService.sendNotification(scheduler);
            } catch (Exception e) {
                log.error("Failed to send email: " + e.getMessage());
            }
            return true;
        }
        catch (Exception e)  {
            log.error("Failed to save scheduler: " + e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Scheduler> readAll() {
        try {
            log.info("Read all schedulers");
            return schedulerRepository.findAll();
        }
        catch (Exception e)  {
            log.error("Failed to read all schedulers: " + e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Optional<Scheduler> read(long id) {
        try {
            log.info("Read scheduler by id = {}", id);
            return schedulerRepository.findById(id);
        }
        catch (Exception e)  {
            log.error("Failed to read scheduler by id: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public boolean update(Scheduler scheduler, long id) {
        try {
            log.info("Update scheduler by id = {}", id);
            scheduler.setId(id);
            schedulerRepository.save(scheduler);
            return true;
        }
        catch (Exception e)  {
            log.error("Failed to update scheduler by id: " + e.getMessage());
            return false;
        }
    }
    @Transactional
    public boolean update(Scheduler scheduler) {
        try {
            log.info("Update scheduler");
            schedulerRepository.save(scheduler);
            return true;
        }
        catch (Exception e)  {
            log.error("Failed to update scheduler" + e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean delete(long id) {
        log.info("Delete scheduler by id = {}", id);
        schedulerRepository.deleteById(id);
        return true;
    }
}
