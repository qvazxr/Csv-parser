package ru.shift.userimporter.api.dto;

public class FileStatisticDto {
    private Integer insertedLinesCount;
    private Integer updatedLinesCount;
    private Integer errorProcessedLinesCount;

    public Integer getInsertedLinesCount() { return insertedLinesCount; }
    public void setInsertedLinesCount(Integer insertedLinesCount) { this.insertedLinesCount = insertedLinesCount; }

    public Integer getUpdatedLinesCount() { return updatedLinesCount; }
    public void setUpdatedLinesCount(Integer updatedLinesCount) { this.updatedLinesCount = updatedLinesCount; }

    public Integer getErrorProcessedLinesCount() { return errorProcessedLinesCount; }
    public void setErrorProcessedLinesCount(Integer errorProcessedLinesCount) { this.errorProcessedLinesCount = errorProcessedLinesCount; }
}