import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadHistory } from './upload-history';

describe('UploadHistory', () => {
  let component: UploadHistory;
  let fixture: ComponentFixture<UploadHistory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UploadHistory],
    }).compileComponents();

    fixture = TestBed.createComponent(UploadHistory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
