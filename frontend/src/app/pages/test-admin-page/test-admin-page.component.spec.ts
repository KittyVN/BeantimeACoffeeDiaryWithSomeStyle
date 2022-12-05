import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestAdminPageComponent } from './test-admin-page.component';

describe('TestAdminPageComponent', () => {
  let component: TestAdminPageComponent;
  let fixture: ComponentFixture<TestAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TestAdminPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TestAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
