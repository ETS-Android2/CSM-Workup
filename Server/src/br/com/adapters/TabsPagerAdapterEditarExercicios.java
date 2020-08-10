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

package br.com.adapters;


import br.com.GUI.treinamentos.EditarTreinamentoExerciciosAerobicos;
import br.com.GUI.treinamentos.EditarTreinamentoExerciciosAnaerobicos;
import br.com.GUI.treinamentos.MeusExercicios;
import br.com.GUI.treinamentos.NovoExercicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapterEditarExercicios extends FragmentPagerAdapter {

	public TabsPagerAdapterEditarExercicios(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
		    return new MeusExercicios();
	
		case 1:
			return new NovoExercicio();
			
		default:
			return null;
		}
	
	}
	
	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}
