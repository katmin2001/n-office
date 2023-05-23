package com.fis.crm.crm_entity.DTO;

public class CrmProjectSearchDTO {
    private String projectCode;
    private String projectName;
    private String projectStatus;
    private String projectProcess;
    private String managerName;
    private String memberName;

    public CrmProjectSearchDTO(String projectCode, String projectName, String projectStatus, String projectProcess, String managerName, String memberName) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.projectProcess = projectProcess;
        this.managerName = managerName;
        this.memberName = memberName;
    }

    public CrmProjectSearchDTO() {
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectProcess() {
        return projectProcess;
    }

    public void setProjectProcess(String projectProcess) {
        this.projectProcess = projectProcess;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
