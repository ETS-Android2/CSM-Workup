//    This file is part of WorkUp.
//
//    WorkUp is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    WorkUp is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with WorkUp.  If not, see <http://www.gnu.org/licenses/>.
//

package br.com.adapters;

import br.com.GUI.aulas.Agenda;
import br.com.GUI.avaliacoes.AvaliarGorduraCorporal;
import br.com.GUI.avaliacoes.AvaliacoesAluno;
import br.com.GUI.perfil.MeusAlunos;
import br.com.GUI.perfil.PerfilPersonal;
import br.com.GUI.treinamentos.GerenciarEdicaoDeExercicios;
import br.com.GUI.treinamentos.MeusTreinamentos;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapterHomePersonal extends FragmentPagerAdapter {

	public TabsPagerAdapterHomePersonal(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
	
		switch (index) {
		case 0:
		    return new MeusAlunos();
		case 1:
			return new Agenda();
		case 2:
			return new MeusTreinamentos();
		case 3:
			return new PerfilPersonal();
			
		}
	
		return null;
	}
	

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
