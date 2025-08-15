import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecetaMedicamentoComponent } from './receta-medicamento';

describe('RecetaMedicamento', () => {
  let component: RecetaMedicamentoComponent;
  let fixture: ComponentFixture<RecetaMedicamentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RecetaMedicamentoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecetaMedicamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
