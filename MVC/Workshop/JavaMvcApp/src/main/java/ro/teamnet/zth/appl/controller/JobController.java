package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.appl.domain.Job;
import ro.teamnet.zth.appl.service.JobsService;
import ro.teamnet.zth.appl.service.JobsServiceImpl;

import java.util.List;

/**
 * Created by user on 7/15/2016.
 */

@MyController(urlPath = "/jobs")
public class JobController {

    private final JobsService jobsService = new JobsServiceImpl();

    @MyRequestMethod(urlPath = "/all")
    public List<Job> getAllJobs() {
        return jobsService.findAllJobs();
    }
    @MyRequestMethod(urlPath = "/one")
    public Job getOneJob(@MyRequestParam(name = "id") String id) {
        return jobsService.findOneJob(id);
    }
    @MyRequestMethod(urlPath = "/one", methodType = "DELETE")
    public void deleteOneJob(@MyRequestParam(name="id") String id) {
        jobsService.deleteOneJob(id);
    }
}
