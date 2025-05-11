import { Component, OnInit } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { IncidentService } from '../../api/service/incident.service';
import { MatTable, MatTableDataSource, MatTextColumn } from '@angular/material/table';

@Component({
  selector: 'app-failure-list',
  imports: [
    MatToolbar,
    MatFormField,
    MatLabel,
    MatInput,
    MatFormField,
    MatTable,
    MatTextColumn
  ],
  templateUrl: './failure-list.component.html',
  styleUrl: './failure-list.component.scss',
  standalone: true
})
export class FailureListComponent implements OnInit {

  /* data */
  dataSource = new MatTableDataSource<any>()

  constructor(private incidentService: IncidentService) {
  }

  ngOnInit() {
    this.incidentService.getIncidents().subscribe({
      next: (data) => {
        this.dataSource.data = data;
      },
      error: (error) => {
        console.error('Error fetching incidents:', error);
      }
    });
  }

  applyFilter(event: Event) {
    this.dataSource.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }
}
