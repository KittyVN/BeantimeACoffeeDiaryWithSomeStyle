import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';

@Component({
  selector: 'app-delete-dialog-coffee',
  templateUrl: './delete-dialog-coffee.component.html',
  styleUrls: ['./delete-dialog-coffee.component.css'],
})
export class DeleteDialogCoffeeComponent {
  id: number | undefined;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DeleteDialogCoffeeComponent>,
    private beanService: CoffeeBeanService,
    private snackBar: MatSnackBar
  ) {}

  onNoClick(): void {
    this.dialogRef.close('open');
  }

  deleteCoffee() {
    if (this.data.dataKey !== undefined) {
      this.beanService.delete(this.data.dataKey).subscribe({
        next: data => {
          this.dialogRef.close('closed');
          this.snackBar.open(
            'Successfully deleted the coffee bean',
            'Dismiss',
            {
              duration: 5000,
            }
          );
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
