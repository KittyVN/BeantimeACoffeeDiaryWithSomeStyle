import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ExtractionService } from 'src/services/extraction.service';

@Component({
  selector: 'app-delete-dialog-extraction',
  templateUrl: './delete-dialog-extraction.component.html',
  styleUrls: ['./delete-dialog-extraction.component.css'],
})
export class DeleteDialogExtractionComponent {
  id: number | undefined;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DeleteDialogExtractionComponent>,
    private extractionService: ExtractionService,
    private snackBar: MatSnackBar
  ) {}

  onNoClick(): void {
    this.dialogRef.close('open');
  }

  deleteAccount() {
    if (this.data.dataKey !== undefined) {
      this.extractionService.delete(this.data.dataKey).subscribe({
        next: data => {
          this.dialogRef.close('closed');
        },
        error: error => {
          console.log(error);
          this.snackBar.open(
            'Something went wrong during the deletion',
            'Close',
            {
              duration: 5000,
            }
          );
        },
      });
    }
  }
}
