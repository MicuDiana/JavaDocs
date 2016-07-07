package ro.teamnet.zth.appl.domain;

import ro.teamnet.zth.api.annotations.*;

/**
 * Created by user on 7/7/2016.
 */
public class Job {

    @Id( name = "job_id")
    private Long jobId;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "min_salary")
    private Long minSalary;

    @Column(name = "max_salary")
    private Long maxSalary;

    public Long getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Long getMinSalary() {
        return minSalary;
    }

    public Long getMaxSalary() {
        return maxSalary;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setMinSalary(Long minSalary) {
        this.minSalary = minSalary;
    }

    public void setMaxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (!jobId.equals(job.jobId)) return false;
        if (!jobTitle.equals(job.jobTitle)) return false;
        if (!minSalary.equals(job.minSalary)) return false;
        return maxSalary.equals(job.maxSalary);

    }

}
