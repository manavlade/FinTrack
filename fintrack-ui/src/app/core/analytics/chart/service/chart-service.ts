import { Injectable } from '@angular/core';
import { environment } from '../../../../../environments/environment.development';
import { SalaryChartResponseDTO } from '../models/chart-model';
import { Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ChartService {

  private readonly apiURL = `${environment.apiUrl}/ChartData`

  constructor(private readonly http: HttpClient ) { }

  getChartData(): Observable<SalaryChartResponseDTO> {
    return this.http.get<SalaryChartResponseDTO>(this.apiURL);
  }

}
