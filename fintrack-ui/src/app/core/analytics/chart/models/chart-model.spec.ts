import { TestBed } from '@angular/core/testing';

import { ChartModel } from './chart-model';

describe('ChartModel', () => {
  let service: ChartModel;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChartModel);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
