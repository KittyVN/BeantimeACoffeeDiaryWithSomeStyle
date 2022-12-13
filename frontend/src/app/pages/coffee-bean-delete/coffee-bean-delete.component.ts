import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';

@Component({
  selector: 'app-coffee-bean-delete',
  templateUrl: './coffee-bean-delete.component.html',
  styleUrls: ['./coffee-bean-delete.component.css'],
})
export class CoffeeBeanDeleteComponent implements OnInit {
  constructor(
    private coffeeBeanService: CoffeeBeanService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}
  id: string | null = null;
  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      this.id = paramMap.get('id');
      if (this.id !== null) {
        this.coffeeBeanService.delete(this.id).subscribe({
          next: res => {
            this.router.navigate(['/home']);
          },
          error: err => {
            this.snackBar.open(err.error, 'Close', {
              duration: 5000,
            });
          },
        });
      } else {
        this.snackBar.open('This coffee bean does not exist.', 'Close', {
          duration: 5000,
        });
        this.router.navigate(['/home']);
      }
    });
  }
}
