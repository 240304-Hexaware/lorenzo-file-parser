import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FileService } from '../../services/file/file.service';
import { NgFor, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [ NgFor, NgIf, MatButtonModule ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})

export class DashboardComponent {

  flatFiles: string[] = [];
  flatFileContent: string | null = null;
  flatFileSelected: boolean = false;
  selectedFlatFile: string | null = '';

  constructor(private router: Router, private fileService: FileService) { }

  ngOnInit(): void {
    this.getFlatFiles();
  }
  
  navigateTo(route: string) {
    this.router.navigate([route]);
  }

  toggleFileContent(flatFile: string): void {
    if (this.selectedFlatFile === flatFile) {
        this.selectedFlatFile = null;
    } else {
        this.getFileContent(flatFile);
    }
}

  getFlatFiles() {
    this.fileService.getFlatFiles().subscribe(files => {
      this.flatFiles = files;
    });
  }

  getFileContent(flatFile: string): void {
    if(this.selectedFlatFile == flatFile){
      this.selectedFlatFile = '';
      this.flatFileContent = '';
      return;
    }
    this.selectedFlatFile = flatFile;
    this.fileService.getFlatFileContent(flatFile).subscribe(
        content => {
          this.flatFileContent = content;
          this.flatFileSelected = true;
        },
        error => {
          console.error('Error retrieving file content:', error);
        }
    );
  }

  downloadFlatFile(flatFile: string): void {
    this.fileService.getFlatFileContent(flatFile).subscribe(
      response => {
        const blob = new Blob([response], { type: 'text/plain' });
  
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = flatFile;
  
        link.click();
  
        window.URL.revokeObjectURL(link.href);
      },
      error => {
        console.error('Error downloading flat file:', error);
      }
    );
  }

}