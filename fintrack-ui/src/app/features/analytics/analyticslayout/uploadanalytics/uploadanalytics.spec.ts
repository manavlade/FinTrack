import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Uploadanalytics } from './uploadanalytics';

describe('Uploadanalytics', () => {
  let component: Uploadanalytics;
  let fixture: ComponentFixture<Uploadanalytics>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Uploadanalytics],
    }).compileComponents();

    fixture = TestBed.createComponent(Uploadanalytics);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
