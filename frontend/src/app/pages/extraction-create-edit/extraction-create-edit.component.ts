import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

export enum ExtractionCreateEditMode {
  create,
  edit,
}

@Component({
  selector: 'app-extraction-create-edit',
  templateUrl: './extraction-create-edit.component.html',
  styleUrls: ['./extraction-create-edit.component.css'],
})
export class ExtractionCreateEditComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  id: string | null = null;
  mode: ExtractionCreateEditMode = ExtractionCreateEditMode.create;
  parameterForm = new FormGroup({});
  ratingForm = new FormGroup({
    acidity: new FormControl('', [Validators.min(0)]),
    aromatics: new FormControl('', [Validators.min(0)]),
    sweetness: new FormControl('', [Validators.min(0)]),
    aftertaste: new FormControl('', [Validators.min(0)]),
    ratingNotes: new FormControl('', Validators.maxLength(255)),
  });

  get modeIsCreate(): boolean {
    return this.mode === ExtractionCreateEditMode.create;
  }

  ngOnInit(): void {
    if (this.mode === ExtractionCreateEditMode.edit) {
      this.route.paramMap.subscribe(paramMap => {
        this.id = paramMap.get('id');
      });
    }
  }
}
