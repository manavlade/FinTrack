import { TestBed } from '@angular/core/testing';

import { ExcelUpload } from './excel-upload';

describe('ExcelUpload', () => {
  let service: ExcelUpload;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExcelUpload);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
