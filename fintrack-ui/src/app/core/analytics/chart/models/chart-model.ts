export interface SalaryBucketDTO {
  bucketLabel: string;
  bucketStart: number;
  bucketEnd: number;
  employeeCount: number;
  percentage: number
  mainRange: string
  averageAge: number;
}

export interface SalaryRangeDTO {
  rangeLabel: string;
  totalEmployees: number;
  averageSalary: number;
  percentageOfTotal: number;
  buckets: SalaryBucketDTO[];
}

export interface SalaryChartResponseDTO {
  totalEmployees: number;
  overallAverageSalary: number;
  minSalary: number;
  maxSalary: number;
  highestDensityBucket: string;
  largestRange: string;
  ranges: SalaryRangeDTO[];
  averageAge: number;
  minAge: number;
  maxAge: number;
}