package com.riverburg.eUniversity.model.dto.response.student.total;


public record StudentPlanTotalResponse(int planId, String planName, int max, double received) {

    public StudentPlanTotalResponse(int planId, String planName, int max, double received) {
        this.planId = planId;
        this.planName = planName;
        this.max = max;
        this.received = Math.min(received, max);
    }
}
