import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicamentosListComponent } from './medicamentos-list';

describe('MedicamentosList', () => {
  let component: MedicamentosListComponent;
  let fixture: ComponentFixture<MedicamentosListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MedicamentosListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicamentosListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
