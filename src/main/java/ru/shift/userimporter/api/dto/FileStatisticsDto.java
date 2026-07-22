package ru.shift.userimporter.api.dto;

public class FileStatisticsDto {
    private String fileId;
    private String status;
    private FileStatisticDto statistic;

    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public FileStatisticDto getStatistic() { return statistic; }
    public void setStatistic(FileStatisticDto statistic) { this.statistic = statistic; }
}